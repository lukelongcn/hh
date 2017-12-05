package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.community.BannerTypeAddDTO;
import com.h9.admin.model.dto.community.GoodsTypeAddDTO;
import com.h9.admin.model.dto.community.GoodsTypeEditDTO;
import com.h9.admin.model.dto.transaction.CardCouponsListAddDTO;
import com.h9.admin.service.TransactionService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.dto.transaction.CardCouponsDTO;
import com.h9.common.modle.vo.admin.transaction.CardCouponsVO;
import com.h9.common.modle.vo.admin.transaction.GoodsTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/29 17:48
 */
@RestController
@Api(description = "交易管理")
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Secured(accessCode = "goods_type:add")
    @PostMapping(value="/goods_type")
    @ApiOperation("增加商品类型")
    public Result<GoodsType> addGoodsType(@Validated @RequestBody GoodsTypeAddDTO goodsTypeAddDTO){
        return this.transactionService.addGoodsType(goodsTypeAddDTO);
    }

    @Secured(accessCode = "goods_type:update")
    @PutMapping(value="/goods_type")
    @ApiOperation("编辑商品类型")
    public Result<GoodsType> editGoodsType(@Validated @RequestBody GoodsTypeEditDTO goodsTypeAddDTO){
        return this.transactionService.updateGoodsType(goodsTypeAddDTO);
    }

    @Secured(accessCode = "goods_type:list")
    @GetMapping(value="/goods_type")
    @ApiOperation("获取商品类型列表")
    public Result<PageResult<GoodsTypeVO>> listGoodsType(PageDTO pageDTO){
        return this.transactionService.listGoodsType(pageDTO);
    }

    @GetMapping(value="/goods_type/enable")
    @ApiOperation("获取所有启用商品类型")
    public Result<List<GoodsType>> listEnableGoodsType(){
        return this.transactionService.listEnableGoodsType();
    }

    @Secured(accessCode = "card_coupons:list")
    @GetMapping(value="/card_coupons")
    @ApiOperation("获取卡券列表")
    public Result<PageResult<CardCouponsVO>> listCardCoupons(@Validated CardCouponsDTO cardCouponsDTO){
        return this.transactionService.listCardCoupons(cardCouponsDTO);
    }

    @GetMapping(value="/card_coupons/batch_no")
    @ApiOperation("获取所有卡券批次")
    public Result<List<String>> listCardCouponsBatchNo(@ApiParam(value = "商品id",required = true) Long goodsId){
        return this.transactionService.listCardCouponsBatchNo(goodsId);
    }

    @Secured(accessCode = "card_coupons:batch_add")
    @PostMapping(value="/card_coupons/list")
    @ApiOperation("批量导入卡券")
    public Result addCardCouponsList(@Validated @RequestBody CardCouponsListAddDTO cardCouponsListAddDTO){
        return this.transactionService.addCardCouponsList(cardCouponsListAddDTO);
    }

    @Secured(accessCode = "card_coupons:status:change")
    @PutMapping(value="/card_coupons/{id}/status")
    @ApiOperation("启用/禁用卡券")
    public Result changeCardCouponsStatus(@PathVariable long id){
        return this.transactionService.changeCardCouponsStatus(id);
    }
}
