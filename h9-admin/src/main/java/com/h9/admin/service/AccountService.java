package com.h9.admin.service;

import com.h9.admin.model.vo.*;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.Config;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 账户服务
 * Created by Gonyb on 2017/11/10.
 */
@Service
public class AccountService {
    
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private BalanceFlowRepository balanceFlowRepository;
    @Resource
    private VCoinsFlowRepository vCoinsFlowRepository;
    @Resource
    private BankCardRepository bankCardRepository;
    @Resource
    private ConfigService configService;
    @Resource
    private LotteryLogRepository lotteryLogRepository;
    @Resource
    private UserRecordRepository userRecordRepository;
    
    public Result<PageResult<UserAccountVO>> account(PageDTO pageDTO) {
        Page<UserAccount> all = userAccountRepository.findAll(pageDTO.toPageRequest());
        Page<UserAccountVO> map = all.map(userAccount -> {
            User one = userRepository.findOne(userAccount.getUserId());
            UserAccountVO userAccountVO = UserAccountVO.toUserAccountVO(userAccount);
            if (one != null) {
                userAccountVO.setPhone(one.getPhone());
                userAccountVO.setNickName(one.getNickName());
            } 
            return userAccountVO;
            
        });
        return Result.success(new PageResult<>(map));
    }

    public Result<PageResult<BalanceFlowVO>> accountMoneyFlow(PageDTO pageDTO, Long userId) {
        List<Config> balanceFlowType = configService.getMapListConfig("balanceFlowType");
        Page<BalanceFlow> byBalance = balanceFlowRepository.findByBalance(userId, pageDTO.toPageRequest());
        Page<BalanceFlowVO> map = byBalance.map(balanceFlow -> {
            BalanceFlowVO balanceFlowVO = BalanceFlowVO.toBalanceFlowVOByBalanceFlow(balanceFlow);
            Long flowType = balanceFlow.getFlowType();
            for (Config config : balanceFlowType) {
                if (flowType.toString().equals(config.getKey())) {
                    balanceFlowVO.setFlowTypeDesc(config.getVal());
                }
            }
            return balanceFlowVO;
        });
        
        return Result.success(new PageResult<>(map));
    }

    public Result<PageResult<BalanceFlowVO>> accountVCoinsFlow(PageDTO pageDTO, Long userId) {
        List<Config> balanceFlowType = configService.getMapListConfig("balanceFlowType");
        Page<VCoinsFlow> byBalance = vCoinsFlowRepository.findByBalance(userId, pageDTO.toPageRequest());
        Page<BalanceFlowVO> map = byBalance.map(balanceFlow -> {
            BalanceFlowVO balanceFlowVO = BalanceFlowVO.toBalanceFlowVOByVCoinsFlow(balanceFlow);
            Long flowType = balanceFlow.getvCoinsflowType();
            for (Config config : balanceFlowType) {
                if (flowType.toString().equals(config.getKey())) {
                    balanceFlowVO.setFlowTypeDesc(config.getVal());
                }
            }
            return balanceFlowVO;
        });
        return Result.success(new PageResult<>(map));
    }

    public Result<List<UserBankVO>> bankInfo(Long userId) {
        List<UserBankVO> voByUserId = bankCardRepository.findVOByUserId(userId);
        return Result.success(voByUserId);
    }

    public Result<List<UserRecordVO>> rewardInfo(Date startTime, Date endTime, String key) {
        List<UserRecordVO> userList = lotteryLogRepository.getUserList(startTime, endTime, StringUtils.isEmpty(key) ? null : "%" + key + "%");
        return Result.success(userList);
    }

    public Result<List<ImeiUserRecordVO>> deviceIdInfo(Date startTime, Date endTime) {
        List<ImeiUserRecordVO> userRecordByTime = userRecordRepository.getUserRecordByTime(startTime, endTime);
        userRecordByTime.forEach(imeiUserRecordVO -> imeiUserRecordVO.setRelevanceCount(userRecordRepository.findRelevanceCount(imeiUserRecordVO.getImei())));
        return Result.success(userRecordByTime);
    }
}
