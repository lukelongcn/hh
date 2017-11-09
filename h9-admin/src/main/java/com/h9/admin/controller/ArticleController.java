package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.article.ArticleTypeDTO;
import com.h9.admin.service.ArticleService;
import com.h9.admin.validation.Edit;
import com.h9.common.base.Result;
import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文章
 * Created by Gonyb on 2017/11/9.
 */
@RestController
@Api("文章")
@RequestMapping(value = "/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
    
    @Secured
    @GetMapping(value = "/category")
    @ApiOperation("获取文章类别")
    public Result categoryList(PageDTO pageDTO){
        return articleService.categoryList(pageDTO);
    }

    @Secured
    @GetMapping(value = "/category/{id}")
    @ApiOperation("获取文章类别")
    public Result getCategory(@PathVariable("id") Long id){
        return articleService.getCategory(id);
    }

    @Secured
    @DeleteMapping(value = "/category/{id}")
    @ApiOperation("删除文章类别")
    public Result deleteCategory(@PathVariable("id") Long id){
        return articleService.deleteCategory(id);
    }
    
    @Secured
    @PostMapping(value = "/category")
    @ApiOperation("新增文章类别")
    public Result addCategory(@Validated @RequestBody ArticleTypeDTO articleTypeDTO){
        return articleService.addCategory(articleTypeDTO);
    }

    @Secured
    @PutMapping(value = "/category")
    @ApiOperation("编辑文章类别")
    public Result editCategory(@Validated(Edit.class) @RequestBody ArticleTypeDTO articleTypeDTO){
        return articleService.editCategory(articleTypeDTO);
    }
}
