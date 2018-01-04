package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.hotel.EditHotelDTO;
import com.h9.admin.model.dto.hotel.EditRoomDTO;
import com.h9.admin.service.HotelService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by itservice on 2018/1/4.
 */
@RestController
@Api(description = "酒店相关接口")
public class HotelController {


    @Resource
    private HotelService hotelService;

    @Secured
    @GetMapping(value = "/hotels")
    @ApiOperation("酒店列表")
    public Result hotelList(@RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "20") Integer limit) {

        return hotelService.hotelList(page, limit);
    }


    @Secured
    @PutMapping(value = "/hotel")
    @ApiOperation("酒店编辑")
    public Result editHotel(@Valid @RequestBody EditHotelDTO editHotelDTO) {

        return hotelService.editHotel(editHotelDTO);
    }

    @Secured
    @PostMapping(value = "/hotel")
    @ApiOperation("添加酒店")
    public Result addHotel(@Valid @RequestBody EditHotelDTO editHotelDTO) {
        editHotelDTO.setId(null);
        return hotelService.editHotel(editHotelDTO);
    }


    @Secured
    @GetMapping(value = "/hotel/rooms")
    @ApiOperation("酒店房间列表")
    public Result hotelRoomsList(@RequestParam Long hotelId,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "20") Integer limit) {
        return hotelService.roomList(hotelId,page,limit);
    }

    @Secured
    @PostMapping(value = "/hotel/room")
    @ApiOperation("新增酒店房间")
    public Result addHotel(@Valid@RequestBody EditRoomDTO editRoomDTO) {
        editRoomDTO.setId(null);
        return hotelService.editRoom(editRoomDTO);
    }


    @Secured
    @PutMapping(value = "/hotel/room")
    @ApiOperation("编辑酒店房间")
    public Result editHotel(@Valid@RequestBody EditRoomDTO editRoomDTO) {
        return hotelService.editRoom(editRoomDTO);
    }
}
