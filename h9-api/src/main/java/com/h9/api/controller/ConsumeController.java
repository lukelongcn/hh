package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.service.ConsumeService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * description: 充值、消费接口
 */
@RestController
@RequestMapping("/consume")
public class ConsumeController {

    @Resource
    private ConsumeService consumeService;

    /**
     * description: 手机充值
     */
    @Secured
    @PostMapping("/mobile/recharge")
    public Result mobileRecharge(@RequestBody MobileRechargeDTO mobileRechargeDTO) {

        return consumeService.recharge(mobileRechargeDTO);
    }

    /**
     * description: 获取可充值的面额
     */
    @Secured
    @GetMapping("/mobile/denomination")
    public Result rechargeDenomination(){

        return consumeService.rechargeDenomination();
    }
}
