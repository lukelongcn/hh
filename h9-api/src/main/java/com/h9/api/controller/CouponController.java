package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.CouponService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:优惠券接口</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@RestController
public class CouponController {

    @Resource
    private CouponService couponService;

    @Secured
    @GetMapping("/user/coupon/{state}")
    public Result getUserCoupon(@SessionAttribute("curUserId") Long userId,
                                @PathVariable(value = "state") Integer state,
                                @RequestParam(required = false,defaultValue = "1") Integer page,
                                @RequestParam(required = false,defaultValue = "10") Integer limit){
        return couponService.getUserCoupons(userId,state,page,limit);
    }
}
