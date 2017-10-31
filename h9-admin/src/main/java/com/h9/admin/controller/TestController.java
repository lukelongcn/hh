package com.h9.admin.controller;

import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * TestController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:26
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * description: 手机号登录
     */
    @GetMapping("/hello")
    public Result hello(){
        return Result.success();
    }



}
