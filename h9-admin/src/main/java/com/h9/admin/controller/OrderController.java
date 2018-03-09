package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.WxOrderListInfo;
import com.h9.admin.model.dto.order.ExpressDTO;
import com.h9.admin.model.dto.order.OrderStatusDTO;
import com.h9.admin.model.vo.OrderDetailVO;
import com.h9.admin.model.vo.OrderItemVO;
import com.h9.admin.service.OrderService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.dto.transaction.OrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    /**
     *
     * @param id
     * @param
     * @see com.h9.common.db.entity.PayInfo.OrderTypeEnum
     *
     * @return
     */
    @Secured(accessCode = "order:detail")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取订单详情")
    public Result<OrderDetailVO> orderList(@PathVariable long id
            ,@ApiParam(value = "-1 兼容以前数据 ，2 为微信支付订单详情，") @RequestParam(required = false,defaultValue = "-1") Integer type){
        return orderService.getOrder(id);
    }

    @Secured(accessCode = "order:express:update")
    @PostMapping(value = "/express")
    @ApiOperation("填写/修改订单物流信息")
    public Result<OrderItemVO> editExpress(@Validated @RequestBody ExpressDTO expressDTO){
        return orderService.editExpress(expressDTO);
    }

    @Secured(accessCode = "order:status:update")
    @PostMapping(value = "/{id}/status")
    @ApiOperation("确定|取消订单")
    public Result<OrderItemVO> updateOrderStatus(@PathVariable long id,@Validated @RequestBody OrderStatusDTO orderStatusDTO){
        return orderService.updateOrderStatus(id, orderStatusDTO.getStatus());
    }

    @Secured(accessCode = "express:company")
    @GetMapping(value = "/supportExpress")
    @ApiOperation("获取支持配送的物流公司")
    public Result<List<String>> getSupportExpress(){
        return orderService.getSupportExpress();
    }


    /**
     * description: 微信 支付订单
     * @param  orderType -1 全部 ，1 充值 ，2购买
     */
    @Secured
    @GetMapping("/wx/list")
    @ApiOperation("微信支付对账列表")
    public Result<PageResult<WxOrderListInfo> > wxOrderDetail(@RequestParam(required = false) String wxOrderNo,
                                                       @RequestParam(required = false,defaultValue = "-1") Integer orderType,
                                                       @RequestParam(required = false) Long startTime,
                                                       @RequestParam(required = false) Long endTime,
                                                       @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit){
        return orderService.wxOrderList(page,limit,wxOrderNo,orderType,startTime,endTime);
    }
}
