package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.bigrich.OrdersLotteryRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Ln on 2018/4/15.
 */
public interface OrdersLotteryRelationRep extends BaseRepository<OrdersLotteryRelation> {

    OrdersLotteryRelation findByOrderId(Long orderId);

    Page<OrdersLotteryRelation> findByOrdersLotteryActivityId(Long id, Pageable pageable);
}
