package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.OrderItems;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/10/31.
 */
public interface OrderItemReposiroty extends BaseRepository<OrderItems> {
    /**
     * description: 查询指定用户卡劵个数
     */
    @Query(value = "select count(order_items.id) from orders,order_items where orders.user_id = ?1 and order_items.orders_id = orders.id and orders.order_type = ?2"
            , nativeQuery = true)
    Object findCardCount(Long userId,int orderType);

    /**
     * description: 查询指定用户，订单类别
     */
    @Query(value = "select order_items.* from orders,order_items where orders.user_id = ?1 and order_items.orders_id = orders.id and orders.order_type = ?2",
            nativeQuery = true)
    List<OrderItems> findByUser(Long userId,int orderType);
}
