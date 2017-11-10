package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.service.OrderService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单
 * Created by Gonyb on 2017/11/9.
 */
@RestController
@Api()
@RequestMapping(value = "/order")
public class OrderController {
    
    @Resource
    private OrderService orderService;
    
    @Secured
    @GetMapping(value = "/list")
    @ApiOperation("获取订单列表")
    public Result<PageResult<Orders>> articleList(PageDTO pageDTO){
        return null;
    }
    
    
}
