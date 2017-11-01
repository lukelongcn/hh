package com.h9.api.service;

import com.h9.common.db.entity.Orders;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/10/31.
 */
@Service
@Transactional
public class OrderService {

    public Orders initOrder(String nickName,BigDecimal money,String tel){
        Orders order = new Orders();
        order.setUserName(nickName);
        order.setPayMoney(money);
        order.setNo("");
        order.setPayMethond(1);
        order.setUserPhone(tel);
        order.setSupplierName("欧飞");
        order.setPayStatus(1);
        order.setStatus(1);
        return order;
    }
}
