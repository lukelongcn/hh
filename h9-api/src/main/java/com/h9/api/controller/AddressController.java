package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.AddressDTO;
import com.h9.api.service.AddressService;
import com.h9.common.base.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by 李圆 on 2017/11/27
 */
@RestController
@RequestMapping("/address")
@Api(value = "收货地址管理",description = "收货地址管理")
public class AddressController {

    @Resource
    AddressService addressService;

    /**
     * 获取地址列表
     * @param userId
     * @return//@NotNull(message = "参数不能为空")@PathVariable("userId")Long userId){
     */
    @Secured
    @ApiOperation(value = "获取地址列表")
    @GetMapping(value = "/allAddresses")
    public Result allAddress(@SessionAttribute("curUserId")Long userId){
        return addressService.allAddress(userId);
    }


    /**
     * 所有省
     * @return
     */
    @GetMapping(value = "/allProvinces")
    public Result allProvices(){
        return addressService.allProvices();
    }
    /**
     * 省内所有市
     * @return
     */
    @GetMapping(value = "/allCities/{pid}")
    public Result allCities(@NotNull(message = "请选择收货省")@PathVariable("pid")Long pid){
        return addressService.allCities(pid);
    }



    /**
     * 市内所有区
     * @param userId
     * @param addressDTO
     * @return

    @GetMapping(value = "/allDisticts/{cid}")
    public Result allDisticts(){
        return addressService.allDisticts();
    }*/

    /**
     * 添加收货地址
     * @param userId
     * @param addressDTO
     * @return
     */
    @Secured
    @ApiOperation(value = "添加收货地址")
    @PostMapping(value = "/add")
    public Result addAddress(@SessionAttribute("curUserId")Long userId,@Valid@RequestBody AddressDTO addressDTO){
        return addressService.addAddress(userId,addressDTO);
    }


}
