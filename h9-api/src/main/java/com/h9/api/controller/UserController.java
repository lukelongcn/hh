package com.h9.api.controller;

import com.h9.api.service.UserService;
import com.h9.common.base.Result;
import org.hibernate.annotations.Source;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/10/26.
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;
    @PostMapping("/user/login")
    public Result phoneLogin(){
        return null;
    }
}
