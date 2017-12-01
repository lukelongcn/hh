package com.h9.store.controller;

import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itservice on 2017/11/29.
 */
@RestController
public class HelloController {


    @GetMapping("/hello")
    public Result hello(){
        System.out.println("kdfsfdsfdsfdsddddddddddddd");
        show();
        show2();
        return Result.success();
    }

    public void show(){
        System.out.println("show................");
    }

    public void show2(){
        System.out.println("show2222................");
    }
}
