package com.h9.admin.service;

import com.h9.admin.model.vo.CouponVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.Coupon;
import com.h9.common.db.repo.CouponRespository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/8
 */
@Service
public class CouponService {
    @Resource
    private CouponRespository couponRespository;


    public Result coupons(Integer page, Integer limit) {
        PageResult<Coupon> pageResult = couponRespository.findAll(page, limit);
        if (pageResult == null) {
            return Result.fail("暂无记录");
        }
        return Result.success(pageResult.result2Result(CouponVO::new));
    }
}
