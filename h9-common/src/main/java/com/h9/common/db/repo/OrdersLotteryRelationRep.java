package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.bigrich.OrdersLotteryRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/4/15.
 */
public interface OrdersLotteryRelationRep extends BaseRepository<OrdersLotteryRelation> {
    @Query("select o from OrdersLotteryRelation  o where o.orderId = ?1")
    OrdersLotteryRelation findByOrderId(Long orderId);

    @Query("select o from OrdersLotteryRelation  o where o.ordersLotteryActivityId = ?1 and o.delFlag = 0")
    Page<OrdersLotteryRelation> findByOrdersLotteryActivityId(Long id, Pageable pageable);

    @Query("select o from OrdersLotteryRelation  o where o.ordersLotteryActivityId = ?1 and o.delFlag = 0")
    List<OrdersLotteryRelation> findByOrdersLotteryActivityId(Long id);

    @Query("select o from OrdersLotteryRelation  o where o.delFlag <> 1 and o.userId = ?1 group by o.orderId order by o.id desc ")
    Page<OrdersLotteryRelation> findByOrdersLotteryUserId(Long id, Pageable pageable);
}
