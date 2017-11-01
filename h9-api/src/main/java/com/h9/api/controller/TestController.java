package com.h9.api.controller;


import com.h9.common.base.Result;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.*;


/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * TestController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:26
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试相关接口",description = "测试相关接口")
public class TestController {

    /**
     * description: 手机号登录
     */
    @GetMapping("/hello")
    public Result phoneLogin(){
        return Result.success();
    }



}
