package com.h9.store.controller;

import com.h9.common.base.Result;
import com.h9.store.interceptor.Secured;
import com.h9.store.modle.dto.ConvertGoodsDTO;
import com.h9.store.service.GoodService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by itservice on 2017/11/28.
 */
@RestController
public class GoodsController {


    @Resource
    private GoodService goodService;
    private Logger logger = Logger.getLogger(this.getClass());

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
     * description: 商品详情
     *
     */
    @Secured
    @GetMapping("/goods/{id}")
    public Result goodsDetail(@PathVariable Long id,@SessionAttribute("curUserId") Long userId){
        return goodService.goodsDetail(id,userId);
    }


    /**
     * description: 兑换商品
     */
    @Secured
    @PostMapping("/goods/convert")
    public Result convertGoods(@Valid@RequestBody ConvertGoodsDTO convertGoodsDTO, @SessionAttribute("curUserId") Long userId){
        try {
            return goodService.convertGoods(convertGoodsDTO,userId);
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            return Result.fail("兑换失败，请稍后再试");
        }
    }


}
