package com.h9.api.controller;

import com.h9.api.service.HomeService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/10/30.
 */
@RestController
@Api(value = "充值接口",description = "首页相关接口")
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping("/home")
    public Result homeData(){

        return homeService.homeDate();
    }
}
