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

    /**
     * 我的优惠劵列表
     *
     * @param userId userId
     * @param state  使用状态 1未使用 2已使用 3已过期
     * @param page   page
     * @param limit  limit
     * @return result
     */
    @Secured
    @GetMapping("/user/coupon/{state}")
    public Result getUserCoupon(@SessionAttribute("curUserId") Long userId,
                                @PathVariable(value = "state") Integer state,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return couponService.getUserCoupons(userId, state, page, limit);
    }

    /**
     * 商品可用的优惠劵列表
     *
     * @param userId  userId
     * @param goodsId goodsId
     * @return result
     */
    @Secured
    @GetMapping("/user/coupons")
    public Result getOrderCoupons(@SessionAttribute("curUserId") Long userId,
                                  @RequestParam Long goodsId) {
        return couponService.getOrderCoupons(userId, goodsId);
    }

    /**
     * 赠送优惠劵接口
     *
     * @param userId
     * @param userCouponId
     * @return
     */
    @Secured
    @PostMapping("/user/coupons/send/{userCouponId}")
    public Result sendCoupon(@SessionAttribute("curUserId") Long userId,
                             @PathVariable Long userCouponId) {
        return couponService.sendCoupon(userId, userCouponId);
    }

    /**
     * 领取优惠劵
     *
     * @param userId
     * @param uuid
     * @return
     */
    @Secured
    @PutMapping("/user/coupons/send/{uuid}")
    public Result receiveCoupon(@SessionAttribute("curUserId") Long userId,
                                @PathVariable String uuid) {
        return couponService.receiveCoupon(userId, uuid);
    }

}
