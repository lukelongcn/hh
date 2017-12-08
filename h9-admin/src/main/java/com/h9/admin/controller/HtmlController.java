package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.html.HtmlContentDTO;
import com.h9.admin.service.HtmlService;
import com.h9.admin.validation.Edit;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.HtmlContent;
import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;

/**
 * 单网页
 * Created by Gonyb on 2017/11/9.
 */
@RestController
@Api("单网页")
@RequestMapping(value = "/html")
public class HtmlController {

    @Resource
    private HtmlService htmlService;
    
    @Secured(accessCode = "html:list")
    @GetMapping(value = "/list")
    @ApiOperation("获取单网页list")
    public Result<PageResult<HtmlContent>> list(PageDTO pageDTO){
        return htmlService.list(pageDTO);
    }

    @Secured(accessCode = "html:get")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单网页")
    public Result<HtmlContent> getHtml(@PathVariable() Long id){
        return htmlService.getHtml(id);
    }

    @Secured(accessCode = "html:get:code")
    @GetMapping(value = "")
    @ApiOperation("根据code获取单网页")
    public Result<HtmlContent> getHtml(@ApiParam("标识") @Validated @NotBlank(message = "标识不能为空")@RequestParam() String code){
        return htmlService.getHtmlByCode(code);
    }

    @Secured(accessCode = "html:delete")
    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除单网页")
    public Result deleteCategory(@PathVariable Long id){
        return htmlService.deleteHtml(id);
    }

    @Secured(accessCode = "html:add")
    @PostMapping(value = "/")
    @ApiOperation("新增单网页")
    public Result<HtmlContent> addHtml(@Validated @RequestBody HtmlContentDTO htmlContentDTO){
        return htmlService.addHtml(htmlContentDTO);
    }

    @Secured(accessCode = "html:update")
    @PutMapping(value = "/")
    @ApiOperation("编辑单网页")
    public Result<HtmlContent> editHtml(@Validated({Edit.class, Default.class}) @RequestBody HtmlContentDTO htmlContentDTO){
        return htmlService.editHtml(htmlContentDTO);
    }
}
