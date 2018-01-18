package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.HotelOrderSearchDTO;
import com.h9.admin.model.dto.hotel.EditHotelDTO;
import com.h9.admin.model.dto.hotel.EditRoomDTO;
import com.h9.admin.model.vo.HotelListVO;
import com.h9.admin.model.vo.HotelOrderDetail;
import com.h9.admin.model.vo.HotelOrderListVO;
import com.h9.admin.model.vo.HotelRoomListVO;
import com.h9.admin.service.HotelService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public Result<HotelListVO> hotelList(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                         @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        return hotelService.hotelList(pageNumber, pageSize);
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
    public Result<HotelRoomListVO> hotelRoomsList(@RequestParam Long hotelId,
                                                  @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                  @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        return hotelService.roomList(hotelId,pageNumber,pageSize);
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


    @Secured
    @PutMapping(value = "/hotel/status")
    @ApiOperation("修改酒店状态")
    public Result modifyHotelStatus(@RequestParam Long hotelId,@ApiParam("1 为正常 0为禁用")@RequestParam Integer status) {
        return hotelService.modifyHotelStatus(hotelId,status);
    }

    @Secured
    @PutMapping(value = "/hotel/room/status")
    @ApiOperation("修改酒店房间状态")
    public Result modifyHotelRoomStatus(@RequestParam Long hotelId,
                                        @ApiParam("0 为禁用 1为正常")
                                        @RequestParam Integer status,
                                        @RequestParam Long roomId) {
        return hotelService.modifyHotelRoomStatus(hotelId,status,roomId);
    }

    @Secured
    @PutMapping(value = "/hotel/order/status")
    @ApiOperation("更改订单状态,1 确认 2退款")
    public Result changeOrderStatus(@RequestBody Long hotelOrderId,@ApiParam("1 确认 2退款") @RequestBody Integer status) {
        return hotelService.changeOrderStatus(hotelOrderId,status);
    }


    @Secured
    @PostMapping(value = "/hotel/orders")
    @ApiOperation("酒店订单列表")
    public Result<HotelOrderListVO> ordersList(@RequestBody(required = false) HotelOrderSearchDTO hotelOrderSearchDTO,
                                               @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                               @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        return hotelService.ordersList(hotelOrderSearchDTO,pageNumber,pageSize);
    }


    @Secured
    @GetMapping(value = "/hotel/order/detail/{id}")
    @ApiOperation("酒店订单详情")
    public Result<HotelOrderDetail> orderDetail(@PathVariable Long id) {
        return hotelService.orderDetail(id);
    }
}
