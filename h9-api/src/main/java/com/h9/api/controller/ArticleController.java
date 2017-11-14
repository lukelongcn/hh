package com.h9.api.controller;

import com.h9.api.model.vo.ArticleVO;
import com.h9.api.service.ArticleService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * Created by 李圆
 * on 2017/11/5
 */
@Api(description = "文章详情")
@RestController
public class ArticleController {

    @Resource
    ArticleService articleService;

    @ApiOperation(value = "获取文章内容")
    @GetMapping(value = "/article/{id}")
    public Result<ArticleVO> finOne(@PathVariable("id")Long id){
        return  articleService.findOne(id);
    }

}
