package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.basis.*;
import com.h9.admin.service.UserService;
import com.h9.common.modle.vo.ImageVO;
import com.h9.common.modle.vo.SystemUserVO;
import com.h9.common.modle.dto.PageDTO;
import com.h9.admin.service.BasisService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BankType;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.modle.vo.GlobalPropertyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.BindException;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/5 14:32
 */
@RestController
@Api
@RequestMapping(value = "/basis")
public class BasisController {

    @Resource
    private BasisService basisService;
    @Resource
    private UserService userService;

    //@Secured(accessCode = "param:add")
    @PostMapping(value="/param")
    @ApiOperation("增加参数")
    public Result<GlobalPropertyVO> addGlobalProperty(@Validated @RequestBody GlobalPropertyAddDTO globalPropertyAddDTO) throws BindException {
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
    public Result<PageResult<GlobalPropertyVO>> getGlobalProperties(PageDTO pageDTO,@ApiParam(value = "名称或参数标识")
    @RequestParam(required = false) String key){
        return this.basisService.getGlobalProperties(key,pageDTO);
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

    //@Secured(accessCode = "workbench:statistics:lottery")
    @GetMapping(value="/statistics/lottery")
    @ApiOperation("获取统计的积分和奖励数据")
    public Result statisticsLottery(){
        return basisService.statisticsLottery();
    }

    @Secured
    @PostMapping(value="/user")
    @ApiOperation("增加管理员")
    public Result addSystemUser(@Validated @RequestBody SystemUserAddDTO systemUserAddDTO){
        return  this.basisService.addUser(systemUserAddDTO);
    }

    @Secured
    @PutMapping(value="/user")
    @ApiOperation("编辑管理员")
    public Result editSystemUser(@Validated @RequestBody SystemUserEditDTO systemUserEditDTO){
        return this.basisService.updateUser(systemUserEditDTO);
    }

    @Secured
    @PutMapping(value="/user/{id}/status")
    @ApiOperation("禁用/启用管理员")
    public Result editSystemUserStatus(@PathVariable long id){
        return this.basisService.updateUserStatus(id);
    }

    @Secured
    @GetMapping(value="/user/page")
    @ApiOperation("分页获取管理员")
    public Result<PageResult<SystemUserVO>> getSystemUsers(PageDTO pageDTO){
        return this.basisService.getUsers(pageDTO);
    }


    @Secured
    @PostMapping(value="/image")
    @ApiOperation("增加图片")
    public Result addImage(@Validated @RequestBody ImageAddDTO imageAddDTO){
        return  this.basisService.addImage(imageAddDTO);
    }

    @Secured
    @PutMapping(value="/image")
    @ApiOperation("修改图片")
    public Result addImage(@Validated @RequestBody ImageEditDTO imageEditDTO){
        return  this.basisService.updateImage(imageEditDTO);
    }

    @Secured
    @GetMapping(value="/image/page")
    @ApiOperation("分页获取图片")
    public Result<PageResult<ImageVO>> getImages(PageDTO pageDTO,@ApiParam(value = "标题")
    @RequestParam(required = false) String key){
        return this.basisService.getImages(key, pageDTO);
    }

    @Secured
    @GetMapping(value="/image/folders")
    @ApiOperation("获取图片可选所属文件夹")
    public Result<List<String>> getImageFolders() {
        return this.basisService.getImageFolders();
    }
}
