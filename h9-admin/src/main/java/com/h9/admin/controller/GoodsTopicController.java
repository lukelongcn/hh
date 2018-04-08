package com.h9.admin.controller;

import com.h9.admin.model.dto.topic.EditGoodsTopicDTO;
import com.h9.admin.model.dto.topic.EditGoodsTopicTypeDTO;
import com.h9.admin.model.vo.GoodsTopicVO;
import com.h9.admin.service.GoodsTopicService;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.GoodsTopicType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by Ln on 2018/4/8.
 * 商品专题接口
 */
@RestController
@Api(value = "商品专题接口")
public class GoodsTopicController {

    @Resource
    private GoodsTopicService goodsTopicService;

    @GetMapping("/goodsTopic/type")
    @ApiOperation(value = "专题列表")
    public Result<GoodsTopicType> goodsTopicType(@RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "1") Integer pageNumber) {
        return goodsTopicService.goodsTopicTypeList(pageSize, pageNumber);
    }

    @PostMapping("/goodsTopic/type")
    @ApiOperation(value = "新增专题类型")
    public Result addGoodsTopicType(@Valid @RequestBody EditGoodsTopicTypeDTO editGoodsTopicDTO) {
        editGoodsTopicDTO.setId(null);
        return goodsTopicService.editGoodsTopicType(editGoodsTopicDTO);
    }

    @PutMapping("/goodsTopic/type")
    @ApiOperation(value = "修改专题类型")
    public Result EditGoodsTopicType(@Valid @RequestBody EditGoodsTopicTypeDTO editGoodsTopictypeDTO) {
        return goodsTopicService.editGoodsTopicType(editGoodsTopictypeDTO);
    }

    @GetMapping("/goodsTopic/type/{topicId}")
    @ApiOperation(value = "专题详情")
    public Result<GoodsTopicVO> goodsTopicDetail(@ApiParam("专题id") @PathVariable Long topicId) {
        return goodsTopicService.topicDetail(topicId);
    }

    @PutMapping("/goodsTopic")
    @ApiOperation(value = "修改专题")
    public Result editGoodsTopic(@Valid @RequestBody EditGoodsTopicDTO editGoodsTopicDTO) {
        return goodsTopicService.editGoodsTopic(editGoodsTopicDTO);
    }
}
