package com.h9.api.controller;

import com.h9.api.service.SignService;
import com.h9.common.base.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * Created by 李圆 on 2017/12/29
 */
@RestController
@RequestMapping("/sign")
public class SignController {

    @Resource
    private SignService signService;

    @GetMapping("/money")
    public Result sign(@NotNull(message = "请登录后签到")@SessionAttribute("curUserId")long userId){
       return signService.sign(userId);
    }


    @GetMapping("/newSign")
    public Result newSign(){
        return signService.newSign();
    }
}
