package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.basis.*;
import com.h9.admin.service.UserService;
import com.h9.common.modle.vo.admin.basis.ImageVO;
import com.h9.common.modle.vo.admin.basis.SystemUserVO;
import com.h9.common.modle.dto.PageDTO;
import com.h9.admin.service.BasisService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.account.BankType;
import com.h9.common.db.entity.config.GlobalProperty;
import com.h9.common.modle.vo.admin.basis.GlobalPropertyVO;
import com.h9.common.modle.vo.admin.basis.VersionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
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
@Api(description = "基础设置")
@RequestMapping(value = "/basis")
public class BasisController {

    @Resource
    private BasisService basisService;
    @Resource
    private UserService userService;

    @Secured(accessCode = "param:add")
    @PostMapping(value="/param")
    @ApiOperation("增加参数")
    public Result<GlobalPropertyVO> addGlobalProperty(@Validated @RequestBody GlobalPropertyAddDTO globalPropertyAddDTO) throws BindException {
        return this.basisService.addGlobalProperty(globalPropertyAddDTO.toGlobalProperty());
    }

    @Secured(accessCode = "param:update")
    @PutMapping(value="/param")
    @ApiOperation("编辑参数")
    public Result<GlobalPropertyVO> editGlobalProperty(@Validated @RequestBody GlobalPropertyEditDTO globalPropertyEditDTO){
        return this.basisService.updateGlobalProperty(globalPropertyEditDTO);
    }

    @Secured(accessCode = "param:list")
    @GetMapping(value="/param/page")
    @ApiOperation("分页获取参数")
    public Result<PageResult<GlobalPropertyVO>> getGlobalProperties(PageDTO pageDTO,@ApiParam(value = "名称或参数标识")
    @RequestParam(required = false) String key){
        return this.basisService.getGlobalProperties(key,pageDTO);
    }

    @Secured(accessCode = "param:get")
    @DeleteMapping(value="/param/{id}")
    @ApiOperation("删除参数")
    public Result<GlobalProperty> deleteGlobalProperty(@PathVariable long id){
        return this.basisService.deleteGlobalProperty(id);
    }

    @Secured(accessCode = "bank:add")
    @PostMapping(value="/bank")
    @ApiOperation("增加银行")
    public Result<BankType> addBankType(@Validated @RequestBody BankTypeAddDTO bankTypeAddDTO){
        return this.basisService.addBankType(bankTypeAddDTO);
    }

    @Secured(accessCode = "bank:update")
    @PutMapping(value="/bank")
    @ApiOperation("编辑银行")
    public Result<BankType> editBankType(@Validated @RequestBody BankTypeEditDTO bankTypeEditDTO){
        return this.basisService.updateBankType(bankTypeEditDTO);
    }

    @Secured(accessCode = "bank:list")
    @GetMapping(value="/bank/page")
    @ApiOperation("分页获取银行")
    public Result<PageResult<BankType>> getBankTypes(PageDTO pageDTO){
        return this.basisService.getBankTypes(pageDTO);
    }

    @Secured(accessCode = "bank:status:change")
    @PutMapping(value="/bank/{id}/status")
    @ApiOperation("启用/禁用银行")
    public Result<BankType> editBankTypeStatus(@PathVariable long id){
        return this.basisService.updateBankTypeStatus(id);
    }

    @Secured(accessCode = "workbench:statistics")
    @GetMapping(value="/statistics")
    @ApiOperation("获取统计数据")
    public Result statisticsLottery(){
        return basisService.statistics();
    }

    @GetMapping(value="/sys/funds/info")
    @ApiOperation("资金数据")
    public Result fundsInfo(@RequestParam(required = false) Long startTime,@RequestParam(required = false) Long endTime){
        return basisService.fundsInfo(startTime,endTime);
    }

    @Secured(accessCode = "user:add")
    @PostMapping(value="/user")
    @ApiOperation("增加管理员")
    public Result addSystemUser(@Validated @RequestBody SystemUserAddDTO systemUserAddDTO){
        return  this.basisService.addUser(systemUserAddDTO);
    }

    @Secured(accessCode = "user:update")
    @PutMapping(value="/user")
    @ApiOperation("编辑管理员")
    public Result editSystemUser(@Validated @RequestBody SystemUserEditDTO systemUserEditDTO){
        return this.basisService.updateUser(systemUserEditDTO);
    }

    @Secured(accessCode = "user:status:change")
    @PutMapping(value="/user/{id}/status")
    @ApiOperation("禁用/启用管理员")
    public Result editSystemUserStatus(@PathVariable long id){
        return this.basisService.updateUserStatus(id);
    }

    @Secured(accessCode = "user:list")
    @GetMapping(value="/user/page")
    @ApiOperation("分页获取管理员")
    public Result<PageResult<SystemUserVO>> getSystemUsers(PageDTO pageDTO){
        return this.basisService.getUsers(pageDTO);
    }


    @Secured(accessCode = "image:add")
    @PostMapping(value="/image")
    @ApiOperation("增加图片")
    public Result addImage(@Validated @RequestBody ImageAddDTO imageAddDTO){
        return  this.basisService.addImage(imageAddDTO);
    }

    @Secured(accessCode = "image:update")
    @PutMapping(value="/image")
    @ApiOperation("修改图片")
    public Result addImage(@Validated @RequestBody ImageEditDTO imageEditDTO){
        return  this.basisService.updateImage(imageEditDTO);
    }

    @Secured(accessCode = "image:list")
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

    @Secured(accessCode = "version:add")
    @PostMapping(value="/version")
    @ApiOperation("增加版本")
    public Result addVersion(@Validated @RequestBody VersionAddDTO versionAddDTO){
        return  this.basisService.addVersion(versionAddDTO);
    }

    @Secured(accessCode = "version:update")
    @PutMapping(value="/version")
    @ApiOperation("编辑版本")
    public Result editVersion(@Validated @RequestBody VersionEditDTO versionEditDTO){
        return  this.basisService.updateVersion(versionEditDTO);
    }

    @Secured(accessCode = "version:delete")
    @DeleteMapping(value="/version/{id}")
    @ApiOperation("删除版本")
    public Result editVersion(@PathVariable long id){
        return  this.basisService.deleteVersion(id);
    }

    @Secured(accessCode = "version:list")
    @GetMapping(value="/version/page")
    @ApiOperation("分页获取版本")
    public Result<PageResult<VersionVO>> getImages(PageDTO pageDTO){
        return this.basisService.listVersion(pageDTO);
    }

    @Secured
    @GetMapping(value="/user/nick_name")
    @ApiOperation("根据手机号码获取昵称")
    public Result<String> getNickName(@NotBlank(message = "请输入手机号码") String phone){
        return this.basisService.getNickNameByPhone(phone);
    }

    @Secured(accessCode = "white_list:add")
    @PostMapping(value="/white_list")
    @ApiOperation("增加白名单")
    public Result addWhiteList(@Validated @RequestBody WhiteListAddDTO whiteListAddDTO){
        return  this.basisService.addWhiteList(whiteListAddDTO);
    }

    @Secured(accessCode = "white_list:update")
    @PutMapping(value="/white_list")
    @ApiOperation("编辑白名单")
    public Result addWhiteList(@Validated @RequestBody WhiteListEditDTO whiteListEditDTO){
        return  this.basisService.updateWhiteList(whiteListEditDTO);
    }

    @Secured(accessCode = "white_list:cancel")
    @PutMapping(value="/white_list/{id}/status")
    @ApiOperation("取消白名单")
    public Result addWhiteList(@PathVariable long id){
        return  this.basisService.cancelWhiteList(id);
    }

    @Secured(accessCode = "white_list:list")
    @GetMapping(value="/white_list")
    @ApiOperation("获取白名单")
    public Result listWhiteList(PageDTO pageDTO){
        return  this.basisService.listWhiteListVO(pageDTO);
    }

}
