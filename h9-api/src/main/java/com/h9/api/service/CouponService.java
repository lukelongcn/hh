package com.h9.api.service;

import com.h9.api.model.vo.OrderCouponsVO;
import com.h9.api.model.vo.UserCouponVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.coupon.CouponGoodsRelation;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.repo.CouponGoodsRelationRep;
import com.h9.common.db.repo.CouponRespository;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.UserCouponsRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private CouponGoodsRelationRep couponGoodsRelationRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;

    /**
     * 用户优惠券
     */
    @Transactional
    public Result getUserCoupons(Long userId, Integer state, Integer page, Integer limit) {
        if (state > 3 || state < 1) {
            return Result.fail("请选择正确的状态");
        }
        PageResult<UserCoupon> pageResult = userCouponsRepository.findState(userId, state, page, limit);
        if (pageResult == null) {
            return Result.fail("暂无可用优惠券");
        }
        return Result.success(pageResult.result2Result(userCoupon -> {

            CouponGoodsRelation couponGoodsRelation = couponGoodsRelationRep.findByCouponIdFirst(userCoupon.getCoupon().getId(), 0);
            Long goodsId = couponGoodsRelation.getGoodsId();
            Goods goods = goodsReposiroty.findOne(goodsId);
            UserCouponVO userCouponVO = new UserCouponVO(userCoupon, goods);
            return userCouponVO;

        }));
    }

    @Transactional
    public Result getOrderCoupons(Long userId, Long goodsId) {

        List<UserCoupon> userCouponList = userCouponsRepository.findByUserId(userId, 1);

        userCouponList = userCouponList.stream().filter(userCoupon -> {
            List<CouponGoodsRelation> relations = couponGoodsRelationRep.findByCouponId(userCoupon.getCoupon().getId(), 0);
            Long gid = relations.get(0).getGoodsId();
            return gid.equals(goodsId);
        }).collect(Collectors.toList());


        if (CollectionUtils.isEmpty(userCouponList)) {

            return Result.fail("暂无可用优惠券");
        }

        List<OrderCouponsVO> vo = userCouponList.stream().map(userCoupon -> {
            Goods goods = goodsReposiroty.findOne(goodsId);
            return new OrderCouponsVO(userCoupon, goods);
        }).collect(Collectors.toList());

        return Result.success(vo);
    }
}
