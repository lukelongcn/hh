package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.coupon.CouponsDTO;
import com.h9.admin.service.CouponService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * @param pageNumber 页码
     * @param pageSize   个数
     * @return pageRequest
     */
    @GetMapping("/coupons")
    public Result coupons(@RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(defaultValue = "1") Integer pageNumber) {
        return couponService.coupons(pageNumber, pageSize);
    }

    /**
     * 新增优惠券
     *
     * @param couponsDTO 请求对象
     * @return result
     */
    @PostMapping("/addCoupons")
    public Result addCoupons(@RequestBody CouponsDTO couponsDTO) {
        return couponService.addCoupons(couponsDTO);
    }

    /**
     * 改变优惠券状态
     *
     * @return result
     */
    @PostMapping("/changeCouponState/{id}/{state}")
    public Result changeCouponState(@PathVariable Long id,
                                    @PathVariable Integer state) {
        return couponService.changeCouponState(id, state);
    }

    /**
     * 编辑优惠券
     *
     * @param couponsDTO 请求对象
     * @return result
     */
    @PostMapping("/updateCoupons/{id}")
    public Result updateCoupons(@PathVariable Long id,
                                @RequestBody CouponsDTO couponsDTO) {
        return couponService.updateCoupons(id, couponsDTO);
    }


    /**
     * 上传优惠劵数据
     *
     * @param file
     * @return
     */
    @Secured()
    @PostMapping(value = "/coupons/file/{couponId}")
    public Result batchRechargeFileUpload(MultipartFile file, @PathVariable Long couponId) {

        return couponService.handlerFile(file,couponId);
    }


    /**
     * 赠送优惠劵接口
     * @param tempId
     * @param couponId
     * @return
     */
    @Secured()
    @PostMapping(value = "/coupons/send")
    public Result sendCoupon2User(@RequestParam String tempId,@RequestParam Long couponId) {

        return couponService.sendCoupon(tempId,couponId);
    }

}
