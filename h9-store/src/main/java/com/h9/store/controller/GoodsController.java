package com.h9.store.controller;

import com.h9.common.base.Result;
import com.h9.common.common.ServiceException;
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
     */
    @Secured
    @GetMapping("/goodsList")
    public Result goodsList(@RequestParam(defaultValue = "o_all") String type,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit) {
        return goodService.goodsList(type, page, limit);
    }

    /**
     * description: 商品详情
     */
    @Secured
    @GetMapping("/goods/{id}")
    public Result goodsDetail(@PathVariable Long id, @SessionAttribute("curUserId") Long userId) {
        return goodService.goodsDetail(id, userId);
    }


    /**
     * description: 兑换商品
     * WX(2, "wx"), WXJS(3, "wxjs")
     *
     * @see com.h9.common.db.entity.order.Orders.PayMethodEnum
     */
    @Secured
    @PostMapping("/goods/convert")
    public Result convertGoods(@Valid @RequestBody ConvertGoodsDTO convertGoodsDTO
            , @SessionAttribute("curUserId") Long userId) {
        return goodService.convertGoods(convertGoodsDTO, userId);
    }



}
