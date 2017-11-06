package com.h9.api.service;

import com.h9.api.model.vo.OrderDetailVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.OrdersReposiroty;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/10/31.
 */
@Service
@Transactional
public class OrderService {

    @Resource
    private OrdersReposiroty ordersReposiroty;
    @Resource
    private UserService userService;

    public Orders initOrder(String nickName, BigDecimal money, String tel,int type,String supplierName) {
        Orders order = new Orders();
        order.setUserName(nickName);
        order.setPayMoney(money);
        order.setNo("");
        order.setPayMethond(1);
        order.setUserPhone(tel);
        order.setSupplierName(supplierName);
        order.setPayStatus(1);
        order.setStatus(1);
        order.setOrderType(type);
        return order;
    }

    public Result orderList(Long userId,Integer page,Integer size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Orders> pageOrders = ordersReposiroty.findByUser(userId, pageRequest);
        Page<OrderListVO> orderListVo = pageOrders.map(order -> OrderListVO.convert(order));
        return Result.success(orderListVo);
    }

    public Result orderDetail(Long orderId) {
        Orders orders = ordersReposiroty.findOne(orderId);
        OrderDetailVO vo = OrderDetailVO.convert(orders);
        return Result.success(vo);
    }
}
