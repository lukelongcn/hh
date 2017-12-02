package com.h9.store.modle.vo;

import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.Orders;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2017/11/30.
 */
@Data
@Accessors(chain = true)
public class OrderListVO {

    private String supplyName;
    private String status;
    private String img;
    private String goodsName;
    private Long orderId;

    public OrderListVO(){}

    public OrderListVO(Orders order){
        Goods goods = order.getOrderItems().get(0).getGoods();
        this.supplyName = order.getSupplierName();
        this.status = "已完成";
        this.img = goods.getImg();
        this.goodsName = goods.getName();
        this.orderId = order.getId();
    }
}
