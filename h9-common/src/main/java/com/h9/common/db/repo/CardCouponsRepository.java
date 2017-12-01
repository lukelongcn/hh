package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.CardCoupons;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by itservice on 2017/11/6.
 */
public interface CardCouponsRepository extends BaseRepository<CardCoupons>{

    /**
     * description: 查询指定类型的滴滴劵库存
     */
    @Query(value = "select count(o.id) from CardCoupons o where o.status = 1 and o.goodsId = ?1 ")
    Object getCount(Long goodId);

    @Query(value = "select * from goods_didi_number where goods_id = ?1 and status = 1 limit 0,1",nativeQuery = true)
    CardCoupons findByGoodsId(Long goodsId);

    @Query(value = "select gdn from CardCoupons gdn where  gdn.didiNumber = ?1")
    CardCoupons findByGoodsAndDidiNumber(String number);


    @Query(value = "select * from goods_didi_number where status = 1 limit 0,1",nativeQuery = true)
    CardCoupons findTopOneUnUse();
}
