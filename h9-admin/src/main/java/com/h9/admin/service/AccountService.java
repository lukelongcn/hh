package com.h9.admin.service;

import com.h9.admin.model.vo.*;
import com.h9.admin.model.vo.UserBankVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.account.VCoinsFlow;
import com.h9.common.db.entity.config.SystemBlackList;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.BlackAccountDTO;
import com.h9.common.modle.dto.BlackIMEIDTO;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.admin.finance.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 账户服务
 * Created by Gonyb on 2017/11/10.
 */
@Service
public class AccountService {
    
    @Resource
    private UserAccountRepository userAccountRepository;
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
    @Resource
    private SystemBlackListRepository systemBlackListRepository;
    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordRepository;
    @Resource
    private AddressRepository addressRepository;
    
    public Result<PageResult<UserAccountVO>> account(PageDTO pageDTO) {
        /*Sort sort = new Sort(Sort.Direction.DESC,"userId");
        Page<UserAccount> all = userAccountRepository.findAll(pageDTO.toPageRequest(sort));
        Page<UserAccountVO> map = all.map(userAccount -> {
            User one = userRepository.findOne(userAccount.getUserId());
            UserAccountVO userAccountVO = UserAccountVO.toUserAccountVO(userAccount);
            if (one != null) {
                userAccountVO.setPhone(one.getPhone());
                userAccountVO.setNickName(one.getNickName());
            } 
            return userAccountVO;
            
        });*/
        Page<UserAccountVO> map = userAccountRepository.findUserAccountVO(pageDTO.toPageRequest());
        return Result.success(new PageResult<>(map));
    }

    public Result<PageResult<BalanceFlowVO>> accountMoneyFlow(PageDTO pageDTO, Long userId) {
        Map balanceFlowType = this.configService.getMapConfig(ParamConstant.BALANCE_FLOW_TYPE);
        Page<BalanceFlow> byBalance = balanceFlowRepository.findByBalance(userId, pageDTO.toPageRequest());
        Page<BalanceFlowVO> map = byBalance.map(balanceFlow -> {
            BalanceFlowVO balanceFlowVO = BalanceFlowVO.toBalanceFlowVOByBalanceFlow(balanceFlow);
            String flowType = balanceFlow.getFlowType().toString();
            String flowTypeDesc = null;
            if (balanceFlowType != null) {
                if (balanceFlowType.get(flowType) != null) {
                    flowTypeDesc = balanceFlowType.get(flowType).toString();
                }
            }
            balanceFlowVO.setFlowTypeDesc(flowTypeDesc);
            return balanceFlowVO;
        });
        
        return Result.success(new PageResult<>(map));
    }

    public Result<PageResult<BalanceFlowVO>> accountVCoinsFlow(PageDTO pageDTO, Long userId) {
        Map balanceFlowType = this.configService.getMapConfig(ParamConstant.VCOIN_EXCHANGE_TYPE);
        Page<VCoinsFlow> byBalance = vCoinsFlowRepository.findByBalance(userId, pageDTO.toPageRequest());
        Page<BalanceFlowVO> map = byBalance.map(balanceFlow -> {
            BalanceFlowVO balanceFlowVO = BalanceFlowVO.toBalanceFlowVOByVCoinsFlow(balanceFlow);
            String flowType = balanceFlow.getvCoinsflowType().toString();
            String flowTypeDesc = null;
            if (balanceFlowType != null) {
                if (balanceFlowType.get(flowType) != null) {
                    flowTypeDesc = balanceFlowType.get(flowType).toString();
                }
            }
            balanceFlowVO.setFlowTypeDesc(flowTypeDesc);
            return balanceFlowVO;
        });
        return Result.success(new PageResult<>(map));
    }

    public Result<PageResult<WithdrawRecordVO>> accountWithdrawFlow(PageDTO pageDTO, Long userId) {
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        PageRequest pageRequest = this.withdrawalsRecordRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize(),sort);
        Page<WithdrawRecordVO> withdrawRecordVOS = this.withdrawalsRecordRepository.findByUserId(userId,pageRequest);
        return Result.success(new PageResult<>(withdrawRecordVOS));
    }

    public Result<List<UserBankVO>> bankInfo(Long userId) {
        List<UserBankVO> voByUserId = bankCardRepository.findVOByUserId(userId);
        return Result.success(voByUserId);
    }

    public Result<List<UserRecordVO>> rewardInfo(Date startTime, Date endTime, String key) {
        List<UserRecordVO> userList = lotteryLogRepository.getUserList(startTime, endTime, StringUtils.isEmpty(key) ? null : "%" + key + "%");
        userList = userList.stream().filter(userRecordVO ->
                systemBlackListRepository.findByUserIdAndStatus(userRecordVO.getUserId(),new Date()) == null) //过滤已加入黑名单的数据
                .collect(Collectors.toList());
        return Result.success(userList);
    }

    public Result<List<ImeiUserRecordVO>> deviceIdInfo(Date startTime, Date endTime) {
        List<ImeiUserRecordVO> userRecordByTime = userRecordRepository.getUserRecordByTime(startTime, endTime);
        userRecordByTime = userRecordByTime.stream().filter(imeiUserRecordVO ->
                systemBlackListRepository.findByImeiAndStatus(imeiUserRecordVO.getImei(),new Date()) == null) //过滤已加入黑名单的数据
                .collect(Collectors.toList());
        userRecordByTime.forEach(imeiUserRecordVO -> 
                imeiUserRecordVO.setRelevanceCount(userRecordRepository.findRelevanceCount(imeiUserRecordVO.getImei())));
        
        return Result.success(userRecordByTime);
    }

    public Result<List<SystemBlackList>> addBlackAccount(BlackAccountDTO blackAccountDTO) {
        Calendar instance = Calendar.getInstance();
        String blackDeadTimeUid = configService.getStringConfig(ParamConstant.BLACK_DEAD_TIME_UID);
        if (!StringUtils.isEmpty(blackDeadTimeUid)) {
            instance.add(Calendar.SECOND, Integer.parseInt(blackDeadTimeUid));
        } else {
            instance.add(Calendar.YEAR, 100);
        }
        Integer status = blackAccountDTO.getStatus();
        for (Long userId : blackAccountDTO.getUserIds()) {
            SystemBlackList byUserIdAndStatus = systemBlackListRepository.findByUserIdAndStatus(userId, new Date());
            if (status == 1) {
                if (byUserIdAndStatus != null) {
                    return Result.fail("此账号已经被加入黑名单了");
                } else {
                    systemBlackListRepository.save(createBlackList(instance, "账号黑名单", userId, null));
                }
            }else if (status == 2) {
                if (byUserIdAndStatus != null) {
                    saveOpenBlackListData(byUserIdAndStatus);
                } else {
                    return Result.fail("你要解禁的账号不存在");
                }
            }
        }
        return Result.success();
    }

    public Result<List<SystemBlackList>> addBlackImei(BlackIMEIDTO blackIMEIDTO) {
        Calendar instance = Calendar.getInstance();
        String blackDeadTimeImei = configService.getStringConfig(ParamConstant.BLACK_DEAD_TIME_IMEI);
        if (!StringUtils.isEmpty(blackDeadTimeImei)) {
            instance.add(Calendar.SECOND, Integer.parseInt(blackDeadTimeImei));
        } else {
            instance.add(Calendar.YEAR, 100);
        }
        
        Integer status = blackIMEIDTO.getStatus();
        for (String imei : blackIMEIDTO.getImeis()) {
            SystemBlackList byUserIdAndStatus = systemBlackListRepository.findByImeiAndStatus(imei, new Date());
            if (status == 1) {
                if (byUserIdAndStatus != null) {
                    return Result.fail("此imei已经被加入黑名单了");
                } else {
                    systemBlackListRepository.save(createBlackList(instance, "imei黑名单", null, imei));
                }
            }else if (status == 2) {
                if (byUserIdAndStatus != null) {
                    saveOpenBlackListData(byUserIdAndStatus);
                } else {
                    return Result.fail("你要解禁的账号不存在");
                } 
            }
        }
        return Result.success();
    }

    private void saveOpenBlackListData(SystemBlackList byUserIdAndStatus) {
        byUserIdAndStatus.setStatus(2);
        byUserIdAndStatus.setUpdateTime(new Date());
        byUserIdAndStatus.setEndTime(new Date());
        systemBlackListRepository.save(byUserIdAndStatus);
    }

    private SystemBlackList createBlackList(Calendar instance,String cause,Long userId,String imei) {
        SystemBlackList systemBlackList = new SystemBlackList();
        systemBlackList.setCause(cause);
        systemBlackList.setStartTime(new Date());
        systemBlackList.setEndTime(instance.getTime());
        systemBlackList.setImei(imei);
        systemBlackList.setStatus(1);
        systemBlackList.setUserId(userId);
        systemBlackList.setCreateTime(new Date());
        return systemBlackList;
    }

    public Result<PageResult<BlackAccountVO>> blackAccountList(PageDTO pageDTO) {
        return Result.success(new PageResult<>(systemBlackListRepository.findAllAccount(new Date(),pageDTO.toPageRequest())));
    }

    public Result<PageResult<BlackAccountVO>> blackIMEIList(PageDTO pageDTO) {
        Page<BlackAccountVO> allImei = systemBlackListRepository.findAllImei(new Date(),pageDTO.toPageRequest());
        allImei.forEach(blackAccountVO -> blackAccountVO.setRelevanceCount(userRecordRepository.findRelevanceCount(blackAccountVO.getImei())));
        return Result.success(new PageResult<>(allImei));
    }

}
