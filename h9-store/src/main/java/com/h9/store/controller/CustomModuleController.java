package com.h9.store.controller;

import com.h9.common.base.Result;
import com.h9.store.interceptor.Secured;
import com.h9.store.modle.dto.AddUserCustomDTO;
import com.h9.store.modle.dto.CustomModuleDTO;
import com.h9.store.modle.dto.AddUserCustomDTO;
import com.h9.store.service.CustomModuleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import javax.validation.Valid;
import java.util.List;
import javax.validation.Valid;

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
     * 用户定制数据
     *
     * @param addUserCustomDTOs
     * @param userId
     * @return
     */
    @Secured
    @PostMapping("/custom/modules/user")
    public Result addUserModules(@RequestBody  List<AddUserCustomDTO> addUserCustomDTOs,
                                 @SessionAttribute("curUserId") Long userId) {
        return customModuleService.addUserCustom(addUserCustomDTOs, userId);
    }


    /**
     * 私人订制立即订购
     */
    @Secured
    @PostMapping("/custom/modules/pay")
    public  Result modelPay(@Valid @RequestBody CustomModuleDTO customModuleDTO
            , @SessionAttribute("curUserId") Long userId){
        return customModuleService.modelPay(customModuleDTO,userId);
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
