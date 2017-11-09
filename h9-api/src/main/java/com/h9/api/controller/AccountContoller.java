package com.h9.api.controller;


import com.h9.api.interceptor.Secured;
import com.h9.api.model.vo.BalanceFlowVO;
import com.h9.api.service.AccountService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * AccountContoller:刘敏华 shadow.l-iu@hey900.com
 * Date: 2017/10/30
 * Time: 18:00
 */
@Api(value = "账户相关信息", description = "账户相关信息")
@RestController
public class AccountContoller {

    @Resource
    private AccountService accountService;

    @Secured
    @ApiOperation(value = "账户余额流水")
    @GetMapping("/account/balance/detail")
    public Result<PageResult<BalanceFlowVO>> getUserBalance(@ApiParam(name = "用户token", value = "token", type = "head")
                                                            @SessionAttribute("curUserId") Long userId,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return accountService.getBalanceFlow(userId, page, limit);
    }


    @Secured
    @ApiOperation(value = "账户V币流水")
    @GetMapping("/account/vCoins/detail")
    public Result<PageResult<BalanceFlowVO>> getVCoinsFlow(@ApiParam(name = "用户token", value = "token", type = "head")
                                                           @SessionAttribute("curUserId") Long userId,
                                                           @RequestParam(value = "page",defaultValue = "1") int page,
                                                           @RequestParam(value = "limit",defaultValue = "10") int limit) {
        return accountService.getVCoinsFlow(userId, page, limit);
    }

    /**
     * description: 账户资金信息
     */
    @Secured
    @GetMapping("/account/info")
    public Result accountInfo(@SessionAttribute("curUserId") Long userId) {
        return accountService.accountInfo(userId);
    }

    /**
     * description: 我的滴滴卡劵列表
     */
    @Secured
    @GetMapping("/account/didicoupons")
    public Result didiCoupones(@SessionAttribute("curUserId") Long userId
            , @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return accountService.couponeList(userId, page, limit);
    }
}
