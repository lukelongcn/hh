package com.h9.api.controller;


import com.h9.common.base.Result;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * TestController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:26
 */
@RestController
@Api(value = "测试相关接口",description = "测试相关接口")
public class TestController {

    /**
     * description: 手机号登录
     */
    @GetMapping("/test/hello")
    public Result phoneLogin(){
        System.out.println("hello 11111111111");
        int i = 1/0;
        show();
        return Result.success();
    }

    @GetMapping("/test/hello2")
    public Result phoneLogin2(){
        int i = 1/0;
        return Result.success();
    }

    public static  void show(){
        System.out.println("im show");
    }

//    @GetMapping("/error")
//    public String error(){
//        System.out.println("er");
//
//        return "e";
//    }

}
