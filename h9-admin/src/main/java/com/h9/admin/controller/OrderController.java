package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.order.ExpressDTO;
import com.h9.admin.model.vo.OrderDetailVO;
import com.h9.admin.model.vo.OrderItemVO;
import com.h9.admin.service.OrderService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.dto.transaction.OrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    
    @Secured(accessCode = "order:list")
    @GetMapping(value = "/list")
    @ApiOperation("获取订单列表")
    public Result<PageResult<OrderItemVO>> orderList(@Validated OrderDTO orderDTO){
        return orderService.orderList(orderDTO);
    }

    @Secured(accessCode = "order:detail")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取订单详情")
    public Result<OrderDetailVO> orderList(@PathVariable long id){
        return orderService.getOrder(id);
    }

    @Secured(accessCode = "order:express:update")
    @PostMapping(value = "/express")
    @ApiOperation("填写/修改订单物流信息")
    public Result<OrderItemVO> editExpress(@Validated @RequestBody ExpressDTO expressDTO){
        return orderService.editExpress(expressDTO);
    }

    @Secured(accessCode = "express:company")
    @GetMapping(value = "/supportExpress")
    @ApiOperation("获取支持配送的物流公司")
    public Result<List<String>> getSupportExpress(){
        return orderService.getSupportExpress();
    }
}
