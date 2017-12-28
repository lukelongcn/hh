package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.vo.*;
import com.h9.admin.service.AccountService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.config.SystemBlackList;
import com.h9.common.modle.dto.BlackAccountDTO;
import com.h9.common.modle.dto.BlackIMEIDTO;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.admin.finance.UserAccountVO;
import com.h9.common.modle.vo.admin.finance.WithdrawRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 账户管理
 * Created by Gonyb on 2017/11/9.
 */
@RestController
@Api()
@RequestMapping(value = "/account")
public class AccountController {
    
    @Resource
    private AccountService accountService;
    
    @Secured(accessCode = "account:list")
    @GetMapping(value = "/list")
    @ApiOperation("获取账户列表")
    public Result<PageResult<UserAccountVO>> accountList(PageDTO pageDTO){
        return accountService.account(pageDTO);
    }

    @Secured(accessCode = "account:money:flow:list")
    @GetMapping(value = "/money/flow/{userId}")
    @ApiOperation("获取钱包流水明细")
    public Result<PageResult<BalanceFlowVO>> accountMoneyFlow(PageDTO pageDTO, @PathVariable Long userId){
        return accountService.accountMoneyFlow(pageDTO,userId);
    }

    @Secured(accessCode = "account:v_coins:flow:list")
    @GetMapping(value = "/vCoins/flow/{userId}")
    @ApiOperation("获取vB流水明细")
    public Result<PageResult<BalanceFlowVO>> accountVCoinsFlow(PageDTO pageDTO, @PathVariable Long userId){
        return accountService.accountVCoinsFlow(pageDTO,userId);
    }

    @Secured(accessCode = "account:withdraw:flow:list")
    @GetMapping(value = "/withdraw/flow/{userId}")
    @ApiOperation("获取提现流水明细")
    public Result<PageResult<WithdrawRecordVO>> accountWithdrawFlow(PageDTO pageDTO, @PathVariable Long userId){
        return accountService.accountWithdrawFlow(pageDTO,userId);
    }

    @Secured(accessCode = "account:bank_card")
    @GetMapping(value = "/{userId}/bankInfo")
    @ApiOperation("获取银行卡信息")
    public Result<List<UserBankVO>> bankInfo(@PathVariable Long userId){
        return accountService.bankInfo(userId);
    }

    @Secured
    @GetMapping(value = "/rewardInfo")
    @ApiOperation("获取用户的获奖信息")
    public Result<List<UserRecordVO>> rewardInfo(@RequestParam(defaultValue = "0") Long startTime,
                                                 @RequestParam(defaultValue = "0") Long endTime,
                                                 @RequestParam(defaultValue = "") String key){
        return accountService.rewardInfo(new Date(startTime),endTime==0?new Date():new Date(endTime),key);
    }

    @Secured
    @GetMapping(value = "/deviceIdInfo")
    @ApiOperation("获取设备编号的信息")
    public Result<List<ImeiUserRecordVO>> deviceIdInfo(@RequestParam(defaultValue = "0") Long startTime,
                                                       @RequestParam(defaultValue = "0") Long endTime){
        return accountService.deviceIdInfo(new Date(startTime),endTime==0?new Date():new Date(endTime));
    }

    @Secured(accessCode = "account:black:user_id:add")
    @PostMapping(value = "/black/Account")
    @ApiOperation("添加账号到黑名单")
    public Result<List<SystemBlackList>> addBlackAccount(@Validated @RequestBody BlackAccountDTO blackAccountDTO){
        return accountService.addBlackAccount(blackAccountDTO);
    }

    @Secured(accessCode = "account:black:imei:add")
    @PostMapping(value = "/black/imei")
    @ApiOperation("添加imei到黑名单")
    public Result<List<SystemBlackList>> addBlackImei(@Validated @RequestBody BlackIMEIDTO blackAccountDTO){
        return accountService.addBlackImei(blackAccountDTO);
    }


    @Secured(accessCode = "account:black:user_id:list")
    @GetMapping(value = "/black/account/list")
    @ApiOperation("获取账号黑名单列表")
    public Result<PageResult<BlackAccountVO>> blackAccountList(PageDTO pageDTO){
        return accountService.blackAccountList(pageDTO);
    }

    @Secured(accessCode = "account:black:imei:list")
    @GetMapping(value = "/black/imei/list")
    @ApiOperation("获取imei黑名单列表")
    public Result<PageResult<BlackAccountVO>> blackIMEIList(PageDTO pageDTO){
        return accountService.blackIMEIList(pageDTO);
    }
}
