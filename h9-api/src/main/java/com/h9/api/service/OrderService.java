package com.h9.api.service;

import com.h9.api.model.vo.OrderDetailVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.OrdersRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static com.h9.common.db.entity.Orders.orderTypeEnum.MATERIAL_GOODS;
import static com.h9.common.db.entity.Orders.orderTypeEnum.VIRTUAL_GOODS;

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
    private UserService userService;

    public Orders initOrder(String nickName, BigDecimal money, String tel,String type,String supplierName) {
        Orders order = new Orders();

        if(type.equals(String.valueOf(MATERIAL_GOODS.getCode()))){
            order.setStatus(Orders.statusEnum.DELIVER.getCode());
        }else{
            order.setStatus(Orders.statusEnum.FINISH.getCode());
        }
        order.setUserName(nickName);
        order.setPayMoney(money);
        order.setNo("");
        order.setPayMethond(Orders.PayMethodEnum.BALANCE_PAY.getCode());
        order.setUserPhone(tel);
        order.setSupplierName(supplierName);
        order.setPayStatus(1);

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
