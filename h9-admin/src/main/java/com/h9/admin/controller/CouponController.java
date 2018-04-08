package com.h9.admin.controller;

import com.h9.admin.service.CouponService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/8
 */
@RestController
public class CouponController {
    @Resource
    private CouponService couponService;

    @GetMapping("/coupons")
    public Result coupons(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer limit){
        return couponService.coupons(page,limit);
    }
}
