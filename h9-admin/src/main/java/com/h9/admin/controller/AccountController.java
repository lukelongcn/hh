package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.vo.BalanceFlowVO;
import com.h9.admin.model.vo.UserAccountVO;
import com.h9.admin.model.vo.UserBankVO;
import com.h9.admin.model.vo.UserRecordVO;
import com.h9.admin.service.AccountService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    
    @Secured
    @GetMapping(value = "/list")
    @ApiOperation("获取账户列表")
    public Result<PageResult<UserAccountVO>> accountList(PageDTO pageDTO){
        return accountService.account(pageDTO);
    }

    @Secured
    @GetMapping(value = "/money/flow/{userId}")
    @ApiOperation("获取钱包流水明细")
    public Result<PageResult<BalanceFlowVO>> accountMoneyFlow(PageDTO pageDTO, @PathVariable Long userId){
        return accountService.accountMoneyFlow(pageDTO,userId);
    }

    @Secured
    @GetMapping(value = "/vCoins/flow/{userId}")
    @ApiOperation("获取vB流水明细")
    public Result<PageResult<BalanceFlowVO>> accountVCoinsFlow(PageDTO pageDTO, @PathVariable Long userId){
        return accountService.accountVCoinsFlow(pageDTO,userId);
    }

    @Secured
    @GetMapping(value = "/{userId}/bankInfo")
    @ApiOperation("获取银行卡信息")
    public Result<List<UserBankVO>> bankInfo(@PathVariable Long userId){
        return accountService.bankInfo(userId);
    }

    @Secured
    @GetMapping(value = "/rewardInfo")
    @ApiOperation("获取用户的获奖信息")
    public Result<List<UserRecordVO>> rewardInfo(@RequestParam Date startTime,
                                                 @RequestParam Date endTime,
                                                 @RequestParam String key){
        return accountService.rewardInfo(startTime,endTime,key);
    }
    
}
