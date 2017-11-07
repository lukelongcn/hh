package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.PageDTO;
import com.h9.admin.model.dto.basis.BankTypeAddDTO;
import com.h9.admin.model.dto.basis.BankTypeEditDTO;
import com.h9.admin.model.dto.basis.GlobalPropertyAddDTO;
import com.h9.admin.model.dto.basis.GlobalPropertyEditDTO;
import com.h9.common.db.entity.BankType;
import com.h9.common.modle.vo.GlobalPropertyVO;
import com.h9.admin.service.BasisService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.GlobalProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: George
 * @date: 2017/11/5 14:32
 */
@RestController
@Api
@RequestMapping(value = "/basis")
public class BasisController {

    @Autowired
    private BasisService basisService;

    @Secured
    @PostMapping(value="/param")
    @ApiOperation("增加参数")
    public Result<GlobalPropertyVO> addGlobalProperty(@Validated @RequestBody GlobalPropertyAddDTO globalPropertyAddDTO){
        return this.basisService.addGlobalProperty(globalPropertyAddDTO.toGlobalProperty());
    }

    @Secured
    @PutMapping(value="/param")
    @ApiOperation("编辑参数")
    public Result<GlobalPropertyVO> editGlobalProperty(@Validated @RequestBody GlobalPropertyEditDTO globalPropertyEditDTO){
        return this.basisService.updateGlobalProperty(globalPropertyEditDTO);
    }

    @Secured
    @GetMapping(value="/param/page")
    @ApiOperation("分页获取参数")
    public Result<PageResult<GlobalPropertyVO>> getActivities(PageDTO pageDTO){
        return this.basisService.getGlobalProperties(pageDTO);
    }

    @Secured
    @DeleteMapping(value="/param/{id}")
    @ApiOperation("删除参数")
    public Result<GlobalProperty> deleteGlobalProperty(@PathVariable long id){
        return this.basisService.deleteGlobalProperty(id);
    }

    @Secured
    @PostMapping(value="/bank")
    @ApiOperation("增加银行")
    public Result<BankType> addBankType(@Validated @RequestBody BankTypeAddDTO bankTypeAddDTO){
        return this.basisService.addBankType(bankTypeAddDTO);
    }

    @Secured
    @PutMapping(value="/bank")
    @ApiOperation("编辑银行")
    public Result<BankType> editBankType(@Validated @RequestBody BankTypeEditDTO bankTypeEditDTO){
        return this.basisService.updateBankType(bankTypeEditDTO);
    }

    @Secured
    @GetMapping(value="/bank/page")
    @ApiOperation("分页获取银行")
    public Result<PageResult<BankType>> getBankTypes(PageDTO pageDTO){
        return this.basisService.getBankTypes(pageDTO);
    }

    @Secured
    @PutMapping(value="/bank/{id}/status")
    @ApiOperation("启用/禁用银行")
    public Result<BankType> editBankTypeStatus(@PathVariable long id){
        return this.basisService.updateBankTypeStatus(id);
    }
}
