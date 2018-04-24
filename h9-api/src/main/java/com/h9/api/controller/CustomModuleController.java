package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.CustomModuleService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Ln on 2018/4/24.
 */
@RestController
public class CustomModuleController {
    @Resource
    private CustomModuleService customModuleService;

//    @Secured
//    @GetMapping("/custom/types")
//    public Result moduleTypes(){
//        return customModuleService.types();
//    }

    @Secured
    @GetMapping("/custom/modules/{type}")
    public Result modules(@PathVariable Long type,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer limit) {
        return customModuleService.modules(type,page,limit);
    }

}
