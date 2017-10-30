package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
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
    private com.h9.api.service.AccountService balanceService;



    @Secured
    @ApiOperation(value = "账户余额流水")
    @GetMapping("/account/balance/detail")
    public Result getUserBalance(@RequestParam("page") int page, @RequestParam("limit") int limit){
        long userId = Long.parseLong(MDC.get("userId"));
        return balanceService.getBalanceFlow(userId,page,limit);
    }


    @Secured
    @ApiOperation(value = "账户V币流水")
    @GetMapping("/account/vCoins/detail")
    public Result getVCoinsFlow(@RequestParam("page") int page,@RequestParam("limit") int limit){
        long userId = Long.parseLong(MDC.get("userId"));
        return balanceService.getVCoinsFlow(userId,page,limit);
    }



}
