package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.DidiCardDTO;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.service.ConsumeService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * description: 充值、消费接口
 */
@RestController
@RequestMapping("/consume")
@Api(value = "充值接口", description = "充值接口")
public class ConsumeController {

    @Resource
    private ConsumeService consumeService;

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * description: 手机充值
     */
    @Secured
    @PostMapping("/mobile/recharge")
    public Result mobileRecharge(
            @SessionAttribute("curUserId") Long userId,
            @Valid @RequestBody MobileRechargeDTO mobileRechargeDTO) {
        try {
            return consumeService.recharge(userId, mobileRechargeDTO);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return Result.fail("充值失败");
        }
    }

    /**
     * description: 获取可充值的面额
     */
    @Secured
    @GetMapping("/mobile/denomination")
    public Result rechargeDenomination() {

        return consumeService.rechargeDenomination();
    }

    /**
     * description: 滴滴卡劵列表
     */
    @Secured
    @GetMapping("/didiCards")
    public Result didiCardList() {

        return consumeService.didiCardList();
    }

    /**
     * description: 兑换滴滴卡
     */
    @Secured
    @PutMapping("/didiCard/convert")
    public Result didiCardConvert(@RequestBody @Valid DidiCardDTO didiCardDTO, @SessionAttribute("curUserId") Long userId) {
        return consumeService.didiCardConvert(didiCardDTO, userId);
    }

    /**
     * description: 提现
     */
    @Secured
    @PostMapping("/withdraw/{bankId}/{code}")
    public Result bankWithdraw(@SessionAttribute("curUserId") Long userId
            , @PathVariable Long bankId, @PathVariable String code) {

        return consumeService.bankWithDraw(userId, bankId, code);
    }

    /**
     * description: 余额充值/test 使用
     */
    @Secured
    @GetMapping("/cz/{userId}")
    public Result cz(@PathVariable Long userId) {
        return consumeService.cz(userId);
    }


    //    /**
//     * description: 扫述充值不没有到账的情况
//     */
//    @PostMapping("/withdraw/scan")
//    public Result orderScan(){
//        return consumeService.scan();
//    }
    @Secured
    @GetMapping("/withraw/info")
    public Result withdraInfo(@SessionAttribute("curUserId") Long userId) {
        return consumeService.withdraInfo(userId);
    }

}
