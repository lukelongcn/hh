package com.h9.store.controller;

import com.h9.common.base.Result;
import com.h9.store.interceptor.Secured;
import com.h9.store.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 李圆 on 2017/11/30.
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * description: 酒元商城兑换的订单
     */
    @Secured
    @GetMapping("/orders")
    public Result myConvertOrder(@SessionAttribute("curUserId") Long userId
            , @RequestParam(defaultValue = "1") Integer page
            ,@RequestParam(defaultValue = "10") Integer limit){
        return orderService.myConvertList(userId,page,limit);
    }
}
