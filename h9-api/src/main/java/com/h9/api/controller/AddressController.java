package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.AddressDTO;
import com.h9.api.service.AddressService;
import com.h9.common.base.Result;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
 * @author
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
     * @return
     */
    @Secured
    @ApiOperation(value = "获取地址列表")
    @GetMapping(value = "/allAddresses")
    /**@NotNull(message = "参数不能为空")@PathVariable("userId")Long userId){*/
    public Result allAddress(@SessionAttribute("curUserId")Long userId,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit){
        return addressService.allAddress(userId,page,limit);
    }


    /**
     * 省市区
     * @return
     */
    @GetMapping(value = "/allAreas")
    public Result allAreas(){
        return addressService.allAreas();
    }


    /**
     * @return 所有省
     */
    @GetMapping(value = "/allProvinces")
    public Result allProvices(){
        return addressService.allProvices();
    }
    /**
     * @return 省内所有市
     */
    @GetMapping(value = "/allCities/{pid}")
    public Result allCities(@NotNull(message = "请选择收货省")@PathVariable("pid")Long pid){
        return addressService.allCities(pid);
    }
    /**
     * @return 市内所有区
     */
    @GetMapping(value = "/allDisticts/{cid}")
    public Result allDisticts(Long cid){
        return addressService.allDisticts(cid);
    }


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

    /**
     * 删除收货地址
     * @param userId
     * @param aid
     * @return
     */
    @Secured
    @ApiOperation(value = "删除收货地址")
    @PutMapping(value = "/delete/{id}")
    public Result deleteAddress(@SessionAttribute("curUserId")Long userId,
                                @NotNull(message = "请确定要删除的地址id")@PathVariable("aid")Long aid){
        return addressService.deleteAddress(userId,aid);
    }

    /**
     * 修改收货地址
     * @param userId
     *
     * @param aid
     * @return
     */
    @Secured
    @ApiOperation(value = "修改收货地址")
    @PostMapping(value = "/update/{id}")
    public Result updateAddress(@SessionAttribute("curUserId")Long userId,
                                @NotNull(message = "请确定要修改的地址id")@PathVariable("aid")Long aid,
                                @Valid@RequestBody AddressDTO addressDTO){
        return addressService.updateAddress(userId,aid,addressDTO);
    }

    /**
     * 设定默认地址
     * @param userId
     * @param aid
     * @return
     */
    @Secured
    @ApiOperation(value = "设定默认地址")
    @PutMapping(value = "/default/{id}")
    public Result defualtAddress(@SessionAttribute("curUserId")Long userId,
                                @NotNull(message = "请确定要设定的默认地址id")@PathVariable("aid")Long aid){
        return addressService.defualtAddress(userId,aid);
    }

    /**
     * description: 查询我的默认地址
     */
    @Secured
    @ApiOperation(value = "查询我的默认地址")
    @GetMapping(value = "/default")
    public Result defualtAddress(@SessionAttribute("curUserId")Long userId){
        return addressService.getDefaultAddress(userId);
    }
}
