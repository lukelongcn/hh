package com.h9.lottery.controller;


import com.h9.common.base.Result;
import com.h9.lottery.service.TestLotteryCodeService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * TestController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:26
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试相关接口",description = "测试相关接口")
public class TestController {

    @Resource
    TestLotteryCodeService testLotteryCodeService;

    /**
     * description: 手机号登录
     */
    @GetMapping("/hello")
    public Result test(){
        return Result.success();
    }


    @GetMapping("/code")
    public Result code(){
        return testLotteryCodeService.createCode();
    }


}
