package com.h9.admin.controller;

import com.h9.admin.model.dto.CouponsDTO;
import com.h9.admin.service.CouponService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 优惠券列表
     * @param page 页码
     * @param limit 个数
     * @return pageRequest
     */
    @GetMapping("/coupons")
    public Result coupons(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer limit){
        return couponService.coupons(page,limit);
    }

    /**
     * 新增优惠券
     * @param couponsDTO 请求对象
     * @return result
     */
    @PostMapping("/addCoupons")
    public Result addCoupons(@RequestBody CouponsDTO couponsDTO){
        return couponService.addCoupons(couponsDTO);
    }

    /**
     * 改变优惠券状态
     * @return result
     */
    @PostMapping("/changeCouponState/{id}/{state}")
    public Result changeCouponState(@PathVariable Long id,
                                    @PathVariable Integer state){
        return couponService.changeCouponState(id,state);
    }

    /**
     * 编辑优惠券
     * @param couponsDTO 请求对象
     * @return result
     */
    @PostMapping("/updateCoupons/{id}")
    public Result updateCoupons(@PathVariable Long id,
                                @RequestBody CouponsDTO couponsDTO){
        return couponService.updateCoupons(id,couponsDTO);
    }

    //todo 赠送优惠券

}
