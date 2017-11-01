package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.service.ConsumeService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * description: 充值、消费接口
 */
@RestController
@RequestMapping("/consume")
@Api(value = "充值接口",description = "充值接口")
public class ConsumeController {

    @Resource
    private ConsumeService consumeService;

    /**
     * description: 手机充值
     */
    @Secured
    @PostMapping("/mobile/recharge")
    public Result mobileRecharge(
            @SessionAttribute("curUserId")Long userId,
            @RequestBody MobileRechargeDTO mobileRechargeDTO) {
        return consumeService.recharge(userId,mobileRechargeDTO);
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
