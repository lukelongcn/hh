package com.h9.api.controller;

import com.h9.api.service.HotelService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by itservice on 2018/1/2.
 */
@RestController
public class HotelController {

    @Resource
    private HotelService hotelService;

    /**
     * description: 酒店详情
     */
    @GetMapping("/hotelDetail")
    public Result hotelDetail(@RequestParam Long hotelId){
        return hotelService.detail(hotelId);
    }

    /**
     * description: 酒店列表
     */
    @GetMapping("/hotelList")
    public Result hotelList(@RequestParam String city,@RequestParam(required = false) String queryKey){
        return hotelService.hotelList(city,queryKey);
    }
}
