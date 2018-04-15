package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.coupon.CouponGoodsRelation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/4/9.
 */
public interface CouponGoodsRelationRep extends BaseRepository<CouponGoodsRelation> {

    @Query("select o from CouponGoodsRelation o where o.couponId = ?1 and o.delFlag = ?2")
    List<CouponGoodsRelation> findByCouponId(Long couponId, Integer delFlag);

    default CouponGoodsRelation findByCouponIdFirst(Long couponId, Integer delFlag) {
        List<CouponGoodsRelation> list = findByCouponId(couponId, delFlag);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Query("select o from CouponGoodsRelation  o where o.couponId = ?1 and o.goodsId = ?2")
    List<CouponGoodsRelation> findByCouponIdAndGoodsId(Long couponId, Long goodsId);



    @Query("update CouponGoodsRelation o set o.delFlag = 1 where o.couponId = ?1 and o.delFlag = 0")
    @Modifying
    Integer removeAllByCouponId(Long couponId);
}
