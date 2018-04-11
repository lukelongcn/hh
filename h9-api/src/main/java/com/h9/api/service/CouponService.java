package com.h9.api.service;

import com.h9.api.model.vo.OrderCouponsVO;
import com.h9.api.model.vo.UserCouponVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.coupon.Coupon;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.coupon.UserCoupon.statusEnum.UN_USE;

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
            Goods goods = null;
            if (couponGoodsRelation != null) {
                Long goodsId = couponGoodsRelation.getGoodsId();
                goods = goodsReposiroty.findOne(goodsId);
            }
            UserCouponVO userCouponVO = new UserCouponVO(userCoupon, goods);
            return userCouponVO;

        }));
    }

    @Transactional
    public Result getOrderCoupons(Long userId, Long goodsId) {

        List<UserCoupon> userCouponList = userCouponsRepository.findByUserId(userId, 1);

        userCouponList = userCouponList.stream().filter(userCoupon -> {
            Coupon coupon = userCoupon.getCoupon();
            Date startTime = coupon.getStartTime();
            Date endTime = coupon.getEndTime();
            Date now = new Date();
            if (startTime.after(now)) {
                return false;
            }
            if (now.after(endTime)) {
                return false;
            }
            int state = userCoupon.getState();
            if (state != UN_USE.getCode()) {
                return false;
            }
            List<CouponGoodsRelation> relations = couponGoodsRelationRep.findByCouponId(userCoupon.getCoupon().getId(), 0);
            if (CollectionUtils.isNotEmpty(relations)) {
                Long gid = relations.get(0).getGoodsId();
                return gid.equals(goodsId);
            }
            return false;
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
