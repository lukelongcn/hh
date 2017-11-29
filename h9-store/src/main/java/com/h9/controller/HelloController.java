package com.h9.controller;

import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itservice on 2017/11/29.
 */
@RestController
public class HelloController {


    @GetMapping("/hello")
    public Result hello(){
        return Result.success();
    }
}
