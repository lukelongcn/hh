package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.PersonalStickService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by 李圆 on 2018/1/19
 */
@RestController
@Api(value = "我的社区相关接口",description = "我的社区相关接口")
@RequestMapping(value = "/personalStick")
public class PersonalStickController {

    @Resource
    private PersonalStickService personalStickService;

    /**
     * 我回复的
     */
    @Secured
    @GetMapping("/comment")
    public Result comment(
            @SessionAttribute("curUserId")long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit){
        return personalStickService.comment(userId,page,limit);
    }

    /**
     * 我发布的
     */
    @Secured
    @GetMapping("/push")
    public Result push( @SessionAttribute("curUserId")long userId,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer limit){

        return personalStickService.getPush(userId,page,limit);
    }

    /**
     * 打赏记录
     */
    @Secured
    @GetMapping("/giveReward")
    public Result giveReward(@SessionAttribute("curUserId")long userId,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit){
        return personalStickService.giveReward(userId,page,limit);
    }

    /**
     * 被打赏记录
     */
    @Secured
    @GetMapping("/rewarded")
    public Result rewarded(//@SessionAttribute("curUserId")long userId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit){
        return personalStickService.rewarded(3,page,limit);
    }
}
