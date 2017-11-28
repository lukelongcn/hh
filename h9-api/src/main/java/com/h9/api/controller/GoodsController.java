package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.GoodService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/28.
 */
@RestController
public class GoodsController {


    @Resource
    private GoodService goodService;

    /**
     * description: 商品列表
     *
     *　商品类型 1今日新品 2日常家居 3食品饮料 4 所有商品　
     */
    @Secured
    @GetMapping("/goodsList")
    public Result goodsList(@RequestParam(defaultValue = "5") Integer type,
                            @RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit) {
        return goodService.goodsList(type,page,limit);
    }

    /**
     * description: 商品详情 //TODO 未完成
     *
     */
    @Secured
    @GetMapping("/goods/{id}")
    public Result goodsDetail(@PathVariable Long id){
        return goodService.goodsDetail(id);
    }
}
