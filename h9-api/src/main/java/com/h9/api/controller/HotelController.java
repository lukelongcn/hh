package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.AddHotelOrderDTO;
import com.h9.api.model.dto.HotelPayDTO;
import com.h9.api.service.HotelService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * description: 酒店接口
 */
@RestController
public class HotelController {

    @Resource
    private HotelService hotelService;

    /**
     * description: 酒店详情
     */
    @GetMapping("/hotel/detail")
    public Result hotelDetail(@RequestParam Long hotelId){
        return hotelService.detail(hotelId);
    }

    /**
     * description: 酒店列表
     */
    @GetMapping("/hotels")
    public Result hotelList(@RequestParam(required = false) String city,@RequestParam(required = false) String queryKey,
                            @RequestParam(required = false,defaultValue = "1") Integer page,
                            @RequestParam(required = false,defaultValue = "10") Integer limit){
        return hotelService.hotelList(city,queryKey,page,limit);
    }

    /**
     * description: 生成酒店预订订单
     */
    @Secured
    @PostMapping("/hotel/order")
    public Result hotelPreOrder(@Valid@RequestBody AddHotelOrderDTO addHotelOrderDTO, @SessionAttribute("curUserId") Long userId){
        return hotelService.addOrder(addHotelOrderDTO,userId);
    }

    /**
     * description: 获取支付信息
     *
     */
    @Secured
    @GetMapping("/hotel/order/pay")
    public Result payInfo(@RequestParam Long hotelOrderId, @SessionAttribute("curUserId") Long userId){
        return hotelService.payInfo(hotelOrderId,userId);
    }

    /**
     * description: 订单列表
     * 1 全部 ，2为有效单 3为待支付 4为退款单
     *
     */
    @Secured
    @GetMapping("/hotel/order")
    public Result orderList(@RequestParam Integer type,
                            @RequestParam(required = false,defaultValue = "1") Integer page,
                            @RequestParam(required = false,defaultValue = "10") Integer limit){
        return hotelService.orderList(type,page,limit);
    }

    /**
     * description: 酒店预定信息
     */
    @Secured
    @GetMapping("/hotel/reserve")
    public Result hotelOptions(){
        return hotelService.hotelOptions();
    }

    /**
     * description: 支付订单
     * 支付方式:
     *       @see com.h9.common.db.entity.hotel.HotelOrder.PayMethodEnum
     */
    @Secured
    @PostMapping("/hotel/order/pay")
    public Result payOrder(@Valid@RequestBody HotelPayDTO hotelPayDTO){
        return hotelService.payOrder(hotelPayDTO);
    }

    /**
     * description: 订单详情
     */
    @Secured
    @GetMapping("/hotel/order/detail")
    public Result orderDetail(@RequestParam Long orderId, @SessionAttribute("curUserId") Long userId){
        return hotelService.orderDetail(orderId,userId);
    }


    /**
     * description: 包含酒店的城市列表
     */
    @GetMapping("/hotel/city")
    public Result hotelCity(){
        return hotelService.hotelCity();
    }

}
