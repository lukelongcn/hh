package com.h9.api.controller;

import com.h9.api.service.GoodsTopicService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created by Ln on 2018/4/9.
 * 商品专题 api 用户使用
 */
@RestController
public class GoodsTopicController {
    @Resource
    private GoodsTopicService goodsTopicService;

    @GetMapping("/goodsTopic/{id}")
    public Result goodsTopicDetail(@PathVariable Long id){
        return goodsTopicService.topicDetail(id);
    }
}

