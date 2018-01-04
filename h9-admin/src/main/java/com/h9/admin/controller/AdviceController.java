package com.h9.admin.controller;

import com.h9.admin.service.UserAdviceService;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 李圆 on 2018/1/4
 */
@RestController
@Description("用户反馈模块")
public class AdviceController {

    @Resource
    private UserAdviceService userAdviceService;
    @Resource
    private ConfigService configService;

    @Description("获取用户反馈信息")
    @GetMapping("/userAdvice")
    public Result userAdvice(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit){
        Map<String,String> mapConfig = configService.getMapConfig(ParamConstant.ADVICE_TYPE);
        System.out.println(mapConfig);
        return userAdviceService.getUserAdvice(page,limit,mapConfig);
    }


}
