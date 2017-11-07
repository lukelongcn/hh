package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.BankCardDTO;

import com.h9.api.service.BankCardService;
import com.h9.common.base.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by 李圆
 * on 2017/11/2
 */
@RestController
@Api(value = "银行卡编辑信息",description = "银行卡编辑信息")
public class BankCardController {

    @Resource
    private BankCardService bankCardService;

    /**
     * 新增银行卡
     * @param bankCardDTO
     * @return
     */
    @ApiOperation(value = "添加银行卡")
    @PostMapping("/bankCard/add")
    public  Result addBankCard(@SessionAttribute("curUserId")long userId,@Valid@RequestBody BankCardDTO bankCardDTO){
        return  bankCardService.addBankCard(userId,bankCardDTO);
    }

    /**
     * 解绑银行卡
     * @param id
     * @param userId
     * @return 提示信息
     */
    @ApiOperation(value = "解绑银行卡")
    @PutMapping("/bankCard/update/{id}")
    public Result updateBankCard(@PathVariable("id")Long id,
                                 @SessionAttribute("curUserId")long userId){
        return bankCardService.updateStatus(id,userId);
     }

    /**
     * description: 银行类型
     */
    @Secured
    @GetMapping("/bankTypes")
    public Result allBankType(){
        return bankCardService.allBank();
    }


    @Secured
    @GetMapping("/my/bankcards")
    public Result myBankCards(@SessionAttribute("curUserId")long userId){
        return bankCardService.getMyBankList(userId);
    }
}
