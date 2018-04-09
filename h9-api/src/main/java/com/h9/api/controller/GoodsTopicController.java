package com.h9.api.controller;

import com.h9.api.model.vo.topic.GoodsTopicDetailVO;
import com.h9.api.service.GoodsTopicService;
import com.h9.common.base.Result;
import io.swagger.annotations.ApiParam;
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
    public Result<GoodsTopicDetailVO> goodsTopicDetail(@ApiParam("专题类型Id") @PathVariable Long id){
        return goodsTopicService.topicDetail(id);
    }
}

