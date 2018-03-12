package com.h9.api.service;

import com.h9.api.model.vo.OrderDetailVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.PayInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static com.h9.common.db.entity.order.Orders.orderTypeEnum.MATERIAL_GOODS;

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
    private PayInfoRepository payInfoRepository;
    @Resource
    private UserService userService;
    @Resource
    private RestTemplate restTemplate;

    public Orders initOrder(String nickName, BigDecimal money, String tel, String type, String supplierName) {
        Orders order = new Orders();

        if (type.equals(String.valueOf(MATERIAL_GOODS.getCode()))) {
            order.setStatus(Orders.statusEnum.DELIVER.getCode());
        } else {
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

    public Result orderList(int status,Long userId, Integer page, Integer size) {
        PageResult<Orders> pageResult = null;
        if(status == -1){
            pageResult = ordersReposiroty.findByUser(userId, page, size);
        }else{
            pageResult = ordersReposiroty.findByUser(userId,status, page, size);
        }

        return Result.success(pageResult.result2Result(OrderListVO::convert));
    }

    public Result orderDetail(Long orderId,Long userId) {
        Orders orders = ordersReposiroty.findOne(orderId);
        if (!orders.getUser().getId().equals(userId)) {
            return Result.fail("无权查看");
        }
        if (orders == null) return Result.fail("订单不存在");

        OrderDetailVO vo = OrderDetailVO.convert(orders);
        return Result.success(vo);
    }




}
