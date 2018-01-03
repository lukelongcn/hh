package com.h9.api.controller;

import com.h9.api.service.GlobalService;
import com.h9.common.base.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiOperation;

/**
 * Created by 李圆 on 2017/12/23
 */
@RestController
public class PropertyController {

    @Resource
    GlobalService globalService;

    @GetMapping("/redirect")
    @ApiOperation(value = "客户端版本 client: IOS:2 安卓：1")
    public Result version(@RequestParam(value = "client")Integer client) {
        return globalService.version(client);
    }
}
