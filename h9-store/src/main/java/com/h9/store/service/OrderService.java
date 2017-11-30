package com.h9.store.service;

import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.OrdersRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/10/31.
 */
@SuppressWarnings("Duplicates")
@Service
@Transactional
public class OrderService {

    @Resource
    private OrdersRepository ordersReposiroty;

    public Orders initOrder(String nickName, BigDecimal money, String tel,String type,String supplierName) {
        Orders order = new Orders();
        order.setUserName(nickName);
        order.setPayMoney(money);
        order.setNo("");
        order.setPayMethond(Orders.PayMethodEnum.BALANCE_PAY.getCode());
        order.setUserPhone(tel);
        order.setSupplierName(supplierName);
        order.setPayStatus(1);
        order.setStatus(1);
        order.setOrderType(type);
        return order;
    }
    public Orders initOrder( BigDecimal money, String tel,String type,String supplierName,User user) {
        Orders order = new Orders();
        order.setUserName(user.getNickName());
        order.setPayMoney(money);
        order.setNo("");
        order.setPayMethond(Orders.PayMethodEnum.BALANCE_PAY.getCode());
        order.setUserPhone(tel);
        order.setSupplierName(supplierName);
        order.setPayStatus(1);
        order.setStatus(1);
        order.setOrderType(type);
        order.setUser(user);
        return order;
    }




}
