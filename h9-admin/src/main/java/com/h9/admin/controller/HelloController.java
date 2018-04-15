package com.h9.admin.controller;

import com.h9.admin.service.ActivityService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Ln on 2018/4/2.
 */
@RestController
public class HelloController {

    @Resource
    private ActivityService activityService;
    @GetMapping("/hello/method2")
    public Result hello(){
//        activityService.method2();
        return Result.success();
    }
}
