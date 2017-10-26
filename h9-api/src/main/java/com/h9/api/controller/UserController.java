package com.h9.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
}
