package com.h9.admin.service;

import com.h9.admin.model.vo.*;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.BlackAccountDTO;
import com.h9.common.modle.dto.BlackIMEIDTO;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.Config;
import com.h9.common.modle.vo.WithdrawRecordVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    @Resource
    private SystemBlackListRepository systemBlackListRepository;
    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordRepository;
    
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
                systemBlackListRepository.findByUserIdAndStatus(userRecordVO.getUserId(),1) == null) //过滤已加入黑名单的数据
                .collect(Collectors.toList());
        return Result.success(userList);
    }

    public Result<List<ImeiUserRecordVO>> deviceIdInfo(Date startTime, Date endTime) {
        List<ImeiUserRecordVO> userRecordByTime = userRecordRepository.getUserRecordByTime(startTime, endTime);
        userRecordByTime.forEach(imeiUserRecordVO -> 
                imeiUserRecordVO.setRelevanceCount(userRecordRepository.findRelevanceCount(imeiUserRecordVO.getImei())));
        userRecordByTime = userRecordByTime.stream().filter(imeiUserRecordVO -> 
                systemBlackListRepository.findByImeiAndStatus(imeiUserRecordVO.getImei(),1) == null) //过滤已加入黑名单的数据
                .collect(Collectors.toList());
        return Result.success(userRecordByTime);
    }

    public Result<List<SystemBlackList>> addBlackAccount(BlackAccountDTO blackAccountDTO) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, 100);
        Integer status = blackAccountDTO.getStatus();
        for (Long userId : blackAccountDTO.getUserIds()) {
            SystemBlackList byUserIdAndStatus = systemBlackListRepository.findByUserIdAndStatus(userId, 1);
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
        return Result.success("添加成功");
    }

    public Result<List<SystemBlackList>> addBlackImei(BlackIMEIDTO blackIMEIDTO) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, 100);
        Integer status = blackIMEIDTO.getStatus();
        for (String imei : blackIMEIDTO.getImeis()) {
            SystemBlackList byUserIdAndStatus = systemBlackListRepository.findByImeiAndStatus(imei, 1);
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
        return Result.success(new PageResult<>(systemBlackListRepository.findAllAccount(pageDTO.toPageRequest())));
    }

    public Result<PageResult<BlackAccountVO>> blackIMEIList(PageDTO pageDTO) {
        Page<BlackAccountVO> allImei = systemBlackListRepository.findAllImei(pageDTO.toPageRequest());
        allImei.forEach(blackAccountVO -> blackAccountVO.setRelevanceCount(userRecordRepository.findRelevanceCount(blackAccountVO.getImei())));
        return Result.success(new PageResult<>(allImei));
    }
}
