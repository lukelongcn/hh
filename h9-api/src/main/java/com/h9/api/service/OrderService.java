package com.h9.api.service;

import com.h9.api.model.vo.OrderDetailVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.OrdersRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/10/31.
 */
@Service
@Transactional
public class OrderService {

    @Resource
    private OrdersRepository ordersReposiroty;
    @Resource
    private UserService userService;

    public Orders initOrder(String nickName, BigDecimal money, String tel,int type,String supplierName) {
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

    public Result orderList(Long userId,Integer page,Integer size) {
        PageResult<Orders> pageResult = ordersReposiroty.findByUser(userId, page, size);
        return Result.success(pageResult.result2Result(OrderListVO::convert));
    }

    public Result orderDetail(Long orderId) {
        Orders orders = ordersReposiroty.findOne(orderId);
        OrderDetailVO vo = OrderDetailVO.convert(orders);
        return Result.success(vo);
    }


}
