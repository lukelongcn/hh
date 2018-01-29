package com.h9.admin.service;

import com.h9.admin.model.dto.finance.WithdrawRecordQueryDTO;
import com.h9.admin.model.vo.LotteryFlowFinanceVO;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.account.VB2Money;
import com.h9.common.db.entity.lottery.LotteryFlow;
import com.h9.common.db.entity.lottery.LotteryFlowRecord;
import com.h9.common.db.entity.withdrawals.WithdrawalsRecord;
import com.h9.common.modle.vo.admin.finance.LotteryFlowRecordVO;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.admin.finance.WithdrawRecordVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.Constants;
import com.h9.common.db.basis.JpaRepository;
import com.h9.common.modle.dto.LotteryFLowRecordDTO;
import com.h9.common.modle.dto.LotteryFlowFinanceDTO;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author: George
 * @date: 2017/11/9 14:38
 */
@Service
@Transactional
public class FinanceService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JpaRepository jpaRepository;
    @Autowired
    private WithdrawalsRecordRepository withdrawalsRecordRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private LotteryFlowRepository lotteryFlowRepository;
    @Autowired
    private LotteryFlowRecordRepository lotteryFlowRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfigService configService;
    @Autowired
    private VB2MoneyRepository vb2MoneyRepository;

    public Result<PageResult<WithdrawRecordVO>> getWithdrawRecords(WithdrawRecordQueryDTO withdrawRecordQueryDTO) {
        String phone = StringUtils.isBlank(withdrawRecordQueryDTO.getPhone()) ? null : withdrawRecordQueryDTO.getPhone();
        String bankCardNo = StringUtils.isBlank(withdrawRecordQueryDTO.getBankCardNo()) ? null : withdrawRecordQueryDTO.getBankCardNo();
        Page<WithdrawRecordVO> withdrawRecordVOPage = this.withdrawalsRecordRepository.findByCondition(
                phone,bankCardNo,withdrawRecordQueryDTO.getStatus(),withdrawRecordQueryDTO.toPageRequest());
        return Result.success(new PageResult<>(withdrawRecordVOPage));
    }

    public Result<WithdrawalsRecord> updateWithdrawRecordStatus(long id){
        this.logger.infov("提现退回，订单id:{0}",id);
        WithdrawalsRecord withdrawalsRecord = this.withdrawalsRecordRepository.findByLockId(id);
        if(withdrawalsRecord.getStatus()!=WithdrawalsRecord.statusEnum.WITHDRA_EXPCETION.getCode()){
            return Result.fail("该订单不是提现异常订单");
        }
        Result result = this.commonService.setBalance(withdrawalsRecord.getUserId(),withdrawalsRecord.getMoney(), BalanceFlow.BalanceFlowTypeEnum.RETURN.getId(),
                withdrawalsRecord.getId(),withdrawalsRecord.getOrderNo(),"银联退回");
        if(result.getCode()==Result.FAILED_CODE){
            this.logger.errorf("改变用户余额失败,msg:{0}",result.getMsg());
            return Result.fail("改变用户余额失败");
        }
        withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.CANCEL.getCode());
        return Result.success(this.withdrawalsRecordRepository.save(withdrawalsRecord));
    }

    public Result<PageResult<LotteryFlowFinanceVO>> getLotteryFlows(LotteryFlowFinanceDTO lotteryFlowFinanceDTO) throws InvocationTargetException, IllegalAccessException {
        Sort sort  = new Sort(Sort.Direction.DESC,"id");
        Page<LotteryFlow> lotteryFlowPage = this.lotteryFlowRepository.findAll(this.lotteryFlowRepository
                .buildFinanceSpecification(lotteryFlowFinanceDTO),lotteryFlowFinanceDTO.toPageRequest(sort));
        PageResult<LotteryFlow> lotteryFlowPageResult = new PageResult<>(lotteryFlowPage);
        return Result.success(lotteryFlowPageResult.result2Result(LotteryFlowFinanceVO::new));
    }

    public Result transferFromLotteryFlows(Set<Long> ids){
        List<String> errMsgList = new ArrayList<>();
        for(Long id:ids){
            LotteryFlowRecord flowRecord = this.lotteryFlowRecordRepository.findByLotteryFlow_Id(id);
            if(flowRecord!=null){
                errMsgList.add("id为"+id+"的记录已转过账");
                continue;
            }
            LotteryFlow flow = this.lotteryFlowRepository.findByLockId(id);
            Result  result = this.commonService.setBalance(flow.getUser().getId(),flow.getMoney().abs().negate(),BalanceFlow.BalanceFlowTypeEnum.XIAOPINHUI.getId(),
                    flow.getId(), flow.getId().toString(),"小品会");
            flowRecord = new LotteryFlowRecord();
            if(result.getCode()==Result.FAILED_CODE){
                errMsgList.add("手机号:"+flow.getUser().getPhone()+"余额不足");
                flowRecord.setStatus(LotteryFlowRecord.LotteryFlowRecordStatusEnum.FAIL.getId());
            }else{
                flowRecord.setStatus(LotteryFlowRecord.LotteryFlowRecordStatusEnum.SUCCESS.getId());
                long uId = Long.valueOf(this.configService.getStringConfig(Constants.XIAOPINHUI));
                Result r = this.commonService.setBalance(uId,flow.getMoney().abs(),BalanceFlow.BalanceFlowTypeEnum.XIAOPINHUI.getId(),
                        flow.getId(),flow.getId().toString(),"小品会");
                if(r.getCode()==Result.FAILED_CODE){
                    this.logger.errorf("给小品会转账时出错，lotterFlow.id为{0}",flow.getId());
                    errMsgList.add("收款方修改账户余额出错，lotteryFlow.id为"+flow.getId());
                }
            }
            flow.setTransferStatus(LotteryFlow.TransferStatusEnum.TRANSFERRED.getId());
            this.lotteryFlowRepository.save(flow);
            flowRecord.setLotteryFlow(flow);
            flowRecord.setCode(flow.getReward().getCode());
            flowRecord.setMoney(flow.getMoney());
            flowRecord.setNickName(flow.getUser().getNickName());
            flowRecord.setOuterId(flow.getUser().getId());
            flowRecord.setPhone(flow.getUser().getPhone());
            flowRecord.setTransferTime(flow.getCreateTime());
            long userId = Long.valueOf((String) HttpUtil.getHttpSession().getAttribute("curUserId"));
            flowRecord.setUser(this.userRepository.findOne(userId));
            this.lotteryFlowRecordRepository.save(flowRecord);
        }
        if(errMsgList.size()>0){
            return Result.fail(errMsgList.toString());
        }
        return Result.success("成功");
    }

    public Result<PageResult<LotteryFlowRecordVO>> getLotteryFlowRecords(LotteryFLowRecordDTO lotteryFLowRecordDTO) throws InvocationTargetException, IllegalAccessException {
        String phone = StringUtils.isBlank(lotteryFLowRecordDTO.getPhone()) ? null : lotteryFLowRecordDTO.getPhone();
        String code = StringUtils.isBlank(lotteryFLowRecordDTO.getCode()) ? null : lotteryFLowRecordDTO.getCode();
        Date startTime = lotteryFLowRecordDTO.getStartTime();
        Date endTime = null;
        if (lotteryFLowRecordDTO.getEndTime() != null) {
            endTime = DateUtil.addDays(lotteryFLowRecordDTO.getEndTime(),1);
        }
        Integer status = lotteryFLowRecordDTO.getStatus();
        Page<LotteryFlowRecordVO> lotteryFlowRecordPage = this.lotteryFlowRecordRepository.findByCondition(phone, code,
                startTime, endTime, status,lotteryFLowRecordDTO.toPageRequest());
        return Result.success(new PageResult<>(lotteryFlowRecordPage));
    }

    public Result transferFromLotteryFlowRecord(long id){

        LotteryFlowRecord lotteryFlowRecord = this.lotteryFlowRecordRepository.findByLockId(id);
        if(lotteryFlowRecord.getStatus()!=LotteryFlowRecord.LotteryFlowRecordStatusEnum.FAIL.getId()){
            return Result.fail("该记录不为转账失败记录");
        }
        LotteryFlow lotteryFlow =lotteryFlowRecord.getLotteryFlow();
        Result  result = this.commonService.setBalance(lotteryFlow.getUser().getId(),lotteryFlow.getMoney().abs().negate(),BalanceFlow.BalanceFlowTypeEnum.XIAOPINHUI.getId(),
                lotteryFlow.getId(), lotteryFlow.getId().toString(),"小品会");
        if(result.getCode()==Result.FAILED_CODE){
            return Result.fail("余额不足，转账失败");
        }
        long uId = Long.valueOf(this.configService.getStringConfig(Constants.XIAOPINHUI));
        Result r = this.commonService.setBalance(uId,lotteryFlowRecord.getMoney().abs(),BalanceFlow.BalanceFlowTypeEnum.XIAOPINHUI.getId(),
                lotteryFlow.getId(),lotteryFlow.getId().toString(),"小品会");
        if(r.getCode()==Result.FAILED_CODE){
            this.logger.errorf("给小品会转账时出错，lotterFlow.id为{0}",lotteryFlow.getId());
        }
        lotteryFlowRecord.setStatus(LotteryFlowRecord.LotteryFlowRecordStatusEnum.SUCCESS.getId());
        this.lotteryFlowRecordRepository.save(lotteryFlowRecord);
        return Result.success("成功");
    }

    public Result<PageResult<VB2Money>> listVB2Money(String phone, PageDTO pageDTO) {
        if (StringUtils.isBlank(phone)) {
            phone = null;
        }
        Page<VB2Money> vb2MoneyPage = this.vb2MoneyRepository.findByTel(phone,pageDTO.toPageRequest());
        return Result.success(new PageResult<>(vb2MoneyPage));
    }

}
