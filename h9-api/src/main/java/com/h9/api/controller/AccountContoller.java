package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.vo.BalanceFlowVO;
import com.h9.api.service.AccountService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * AccountContoller:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 18:00
 */
@Api(value = "账户相关信息")
@RestController
public class AccountContoller {

    @Resource
    private AccountService accountService;

    @Secured
    @ApiOperation(value = "账户余额流水")
    @GetMapping("/account/balance/detail")
    public Result<PageResult<BalanceFlowVO>> getUserBalance(@RequestParam("page") int page, @RequestParam("limit") int limit){
        long userId = Long.parseLong(MDC.get("userId"));
        return accountService.getBalanceFlow(userId,page,limit);
    }


    @Secured
    @ApiOperation(value = "账户V币流水")
    @GetMapping("/account/vCoins/detail")
    public Result<PageResult<BalanceFlowVO>> getVCoinsFlow(@RequestParam("page") int page, @RequestParam("limit") int limit){
        long userId = Long.parseLong(MDC.get("userId"));
        return accountService.getVCoinsFlow(userId,page,limit);
    }



}
