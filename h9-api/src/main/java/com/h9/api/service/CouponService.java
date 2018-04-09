package com.h9.api.service;

import com.h9.api.model.vo.OrderCouponsVO;
import com.h9.api.model.vo.UserCouponVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.user.UserCoupon;
import com.h9.common.db.repo.CouponRespository;
import com.h9.common.db.repo.UserCouponsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:优惠券相关</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Service
public class CouponService {

    @Resource
    private CouponRespository couponRespository;

    @Resource
    private UserCouponsRepository userCouponsRepository;

    /**
     * 用户优惠券
     */
    @Transactional
    public Result getUserCoupons(Long userId, Integer state, Integer page, Integer limit) {
        PageResult<UserCoupon> pageResult = userCouponsRepository.findState(userId,state, page, limit);
        if (pageResult == null) {
            return Result.fail("暂无可用优惠券");
        }
        return Result.success(pageResult.result2Result(UserCouponVO::new));
    }

    @Transactional
    public Result getOrderCoupons(Long userId, Long goodsId, Integer page, Integer limit) {
        PageResult<UserCoupon> pageResult = userCouponsRepository.findOrderCoupons(userId,goodsId, page, limit);
        if (pageResult == null) {
            return Result.fail("暂无可用优惠券");
        }
        return Result.success(pageResult.result2Result(OrderCouponsVO::new));
    }
}
