package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.SignService;
import com.h9.common.base.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 每日签到
     * 。@param userId
     * @return
     */
    @Secured
    @GetMapping("/daySign")
    public Result sign(@SessionAttribute("curUserId")long userId){
       return signService.sign(userId);
    }

    /**
     * 获取签到页面详情
     * 。。@param userId
     * @return
     */
    @Secured
    @GetMapping("/signMessage")
    public Result signMessage(@SessionAttribute("curUserId")long userId){
        return signService.newSign(userId);
    }


    @Secured
    @GetMapping("/selfSign")
    public Result selfSign(@SessionAttribute("curUserId")long userId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit){
        return signService.selfSign(userId,page,limit);
    }

}
