package com.h9.api.controller;

import com.h9.api.model.dto.BankCardDTO;

import com.h9.api.service.BankCardService;
import com.h9.common.base.Result;

import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankCardRepository;
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
    public  Result addBankCard(@Valid@RequestBody BankCardDTO bankCardDTO,@RequestParam("name") String name){
        return  bankCardService.addBankCard(bankCardDTO,name);
    }

    /**
     * 解绑银行卡
     * @param status 银行卡状态
     * @return 结果信息
     */
    @ApiOperation(value = "解绑银行卡")
    @PutMapping("/bankCard/update/{id}")
    public Result updateBankCard(@PathVariable("id")Long id,
                                 @RequestParam("status")Integer status){
        return bankCardService.updateStatus(id,status);
     }

}
