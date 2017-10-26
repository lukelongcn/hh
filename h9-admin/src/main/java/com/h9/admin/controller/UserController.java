package com.h9.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itservice on 2017/10/26.
 */
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("postTest")
    public String postTest(){
        return "postRest";
    }

    @PutMapping("putTest")
    public String putTest() {
        return "putTest";
    }

    @PostMapping("zeroTest")
    public void zeroTest(){
        int i = 1/0;
    }
}
