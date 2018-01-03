package com.h9.api.controller;

import com.h9.api.service.GlobalService;
import com.h9.api.service.HomeService;
import com.h9.common.annotations.PrintReqResLog;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/10/30.
 */
@RestController
@Api(value = "",description = "首页相关接口")
public class HomeController {

    @Resource
    private HomeService homeService;

    @PrintReqResLog(printResponseResult = true, printRequestParams = true)
    @GetMapping("/home")
    public Result homeData(){
        return homeService.homeDate();
    }

    /**
     * description: 版本升级
     *  101010
     */
    @GetMapping("/version")
    @ApiOperation(value = "版本升级 version:版本 type: IOS:2 安卓：1")
    public Result version(@RequestHeader( value = "version") String version, @RequestHeader(value = "client")Integer client){
        return homeService.version(version,client);
    }

}
