package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.customModule.AddCustomModuleDTO;
import com.h9.admin.model.vo.CustomModuleDetailVO;
import com.h9.admin.model.vo.CustomModuleListVO;
import com.h9.admin.service.CustomModuleService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api("私人定制-.-")
public class CustomModuleController {

    @Resource
    private CustomModuleService customModuleService;

    @Secured
    @PostMapping(value = "/custom/module")
    @ApiOperation("新增私人定制模块")
    public Result addModule(@Valid @RequestBody @ApiParam("参数") AddCustomModuleDTO addCustomModuleDTO,
                            @SessionAttribute("curUserId") Long userId) {
        return customModuleService.addAndEditModule(addCustomModuleDTO, 1, userId);
    }

    @Secured
    @PutMapping(value = "/custom/module")
    @ApiOperation("编辑私人定制模块")
    public Result editModule(@Valid @RequestBody @ApiParam("参数") AddCustomModuleDTO addCustomModuleDTO,
                             @SessionAttribute("curUserId") Long userId) {
        return customModuleService.addAndEditModule(addCustomModuleDTO, 2, userId);
    }

    @Secured
    @GetMapping(value = "/custom/module")
    @ApiOperation("私人定制模块列表")
    public Result<PageResult<CustomModuleListVO>> modules(@ApiParam @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @ApiParam @RequestParam(defaultValue = "1") Integer pageNumber,
                                                          @SessionAttribute("curUserId") Long userId) {
        return customModuleService.modules(pageNumber, pageSize, userId);
    }


    @Secured
    @DeleteMapping(value = "/custom/module/{id}")
    @ApiOperation("删除私人定制模块")
    public Result delete(@PathVariable Long id) {
        return customModuleService.deleteModule(id);
    }


    @Secured
    @GetMapping(value = "/custom/module/{id}")
    @ApiOperation("私人定制模块详情")
    public Result<CustomModuleDetailVO> detail(@PathVariable Long id) {
        return customModuleService.detail(id);
    }
}
