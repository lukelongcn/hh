package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.OrderService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/1.
 */

@RestController
@Api(value = "订单相关接口",description = "订单相关接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * description: 订单列表
     */
    @Secured
    @GetMapping("/orders")
    public Result orderList(@SessionAttribute("curUserId")long userId) {
        return orderService.orderList(userId);
    }

    @Secured
    @GetMapping("/orders/{orderId}")
    public Result orderDetail(@PathVariable Long orderId){
        return orderService.orderDetail(orderId);
    }
}
