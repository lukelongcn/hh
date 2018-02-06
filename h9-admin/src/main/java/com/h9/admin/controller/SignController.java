package com.h9.admin.controller;

import com.h9.admin.service.SignService;
import com.h9.common.base.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 李圆 on 2018/1/15
 */
@RestController
@RequestMapping("/sign")
public class SignController {
    @Resource
    private SignService signService;
    @GetMapping("/getSign")
    public Result getSign(@RequestParam(defaultValue = "1") Integer pageNumber,
                          @RequestParam(defaultValue = "10") Integer pageSize){
        return signService.getSign(pageNumber,pageSize);
    }
}
