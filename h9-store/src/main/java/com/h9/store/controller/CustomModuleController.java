package com.h9.store.controller;

import com.h9.common.base.Result;
import com.h9.store.interceptor.Secured;
import com.h9.store.modle.dto.CustomModuleDTO;
import com.h9.store.service.CustomModuleService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Ln on 2018/4/24.
 */
@RestController
public class CustomModuleController {
    @Resource
    private CustomModuleService customModuleService;

    /**
     * 定制模块列表
     *
     * @param type
     * @param page
     * @param limit
     * @return
     */
    @Secured
    @GetMapping("/custom/modules")
    public Result modules(@RequestParam Long type,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer limit) {
        return customModuleService.modules(type, page, limit);
    }


    /**
     * 定制模块详情
     *
     * @param id
     * @return
     */
    @Secured
    @GetMapping("/custom/modules/detail/{id}")
    public Result modulesDetail(@PathVariable Long id) {
        return customModuleService.modulesDetail(id);
    }


    /**
     * 立即订购订制
     */
    @PostMapping("/custom/modules/pay")
    public  Result modelPay(@RequestBody CustomModuleDTO customModuleDTO){
        return customModuleService.modelPay(customModuleDTO);
    }

    /**
     * 订制可选商品
     */
    @Secured
    @GetMapping("/custom/modules/goods/{id}")
    public Result modelGoods(@SessionAttribute("curUserId")long userId, @PathVariable Long id){
        return customModuleService.modelGoods(userId,id);
    }
}
