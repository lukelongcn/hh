package com.h9.store.controller;

import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 李圆 on 2017/11/29.
 */
@RestController
@RequestMapping("test")
public class TestController {


    @GetMapping("/hello")
    public Result hello(){
        return Result.success();
    }

}
