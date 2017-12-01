package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.HomeService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/10/30.
 */
@RestController
@Api(value = "",description = "首页相关接口")
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping("/home")
    public Result homeData(){

        return homeService.homeDate();
    }

    /**
     * description: 版本升级
     */
    @GetMapping("/version")
    @ApiOperation(value = "版本升级 version:版本 type: IOS:1 安卓：2")
    public Result version(@RequestParam( value = "version") Integer version,@RequestParam(value = "type")Integer type){
        return homeService.version(version,type);
    }


}
