package com.h9.store.controller;

import com.h9.common.annotations.PrintReqResLog;
import com.h9.common.base.Result;
import com.h9.store.interceptor.Secured;
import com.h9.store.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/29.
 */
@RestController
public class HomeController {
    @Resource
    private HomeService homeService;
    /**
     * description: 酒元商城首页
     */
    @Secured
    @GetMapping("/home")
    public Result storeHome(@SessionAttribute("curUserId") Long userId){

        return homeService.storeHome(userId);
    }
}
