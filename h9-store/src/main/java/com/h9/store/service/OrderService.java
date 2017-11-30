package com.h9.store.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.store.modle.vo.OrderListVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/10/31.
 */
@SuppressWarnings("Duplicates")
@Service
@Transactional
public class OrderService {

    @Resource
    private OrdersRepository ordersReposiroty;
    @Resource
    private UserRepository userRepository;

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
    public Orders initOrder( BigDecimal money, String tel,String type,String supplierName,User user,String goodsTypeCode) {
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
        order.setGoodsType(goodsTypeCode);
        return order;
    }

    public Result<List<Orders>> findConvertOrders(int page, int size){
        Page<Orders> orders = ordersReposiroty.findByOrderFrom(1, new PageRequest(page, size));
        List<Orders> orderList = orders.getContent();
        return Result.success(orderList);
    }

    public Result myConvertList(Long userId,int page,int size) {
        PageRequest pageRequest = ordersReposiroty.pageRequest(page, size);
        Page<Orders> orderPage = ordersReposiroty.findByUser(userId, 1, pageRequest);

        List<Orders> orderList = orderPage.getContent();
        if (CollectionUtils.isNotEmpty(orderList)) {
            List<OrderListVO> orderListVO = orderList.stream().map(el -> new OrderListVO(el)).collect(Collectors.toList());
            return Result.success(orderListVO);
        }
        return Result.success();
    }

}
