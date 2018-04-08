package com.h9.admin.controller;

import com.h9.admin.model.dto.topic.EditGoodsTopicModuleDTO;
import com.h9.admin.model.dto.topic.EditGoodsTopicTypeDTO;
import com.h9.admin.model.vo.GoodsTopicModuleVO;
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
@Api(description = "商品专题接口")
public class GoodsTopicController {

    @Resource
    private GoodsTopicService goodsTopicService;

    @GetMapping("/goodsTopic/type")
    @ApiOperation(value = "专题类型列表")
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
    public Result editGoodsTopicType(@Valid @RequestBody EditGoodsTopicTypeDTO editGoodsTopictypeDTO) {

        Long id = editGoodsTopictypeDTO.getId();
        if (id == null) {
            return Result.fail("id不为空");
        }
        return goodsTopicService.editGoodsTopicType(editGoodsTopictypeDTO);
    }

    @GetMapping("/goodsTopic/module/{id}")
    @ApiOperation(value = "专题模块详情")
    public Result<GoodsTopicVO> goodsTopicDetail(@ApiParam("专题模块id") @PathVariable Long id) {
        return goodsTopicService.topicDetail(id);
    }

    @PutMapping("/goodsTopic/module")
    @ApiOperation(value = "修改专题模块")
    public Result editGoodsTopic(@Valid @RequestBody EditGoodsTopicModuleDTO editGoodsTopicModuleDTO) {
        return goodsTopicService.editGoodsTopicModule(editGoodsTopicModuleDTO);
    }

    @PostMapping("/goodsTopic/module")
    @ApiOperation(value = "添加专题模块")
    public Result addGoodsTopic( @RequestBody EditGoodsTopicModuleDTO editGoodsTopicModuleDTO) {
        return goodsTopicService.addGoodsTopicModule(editGoodsTopicModuleDTO);
    }


    @GetMapping("/goodsTopic/module")
    @ApiOperation(value = "专题模快列表")
    public Result<GoodsTopicModuleVO> goodsTopicModule(@RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(defaultValue = "1") Integer pageNumber) {
        return goodsTopicService.goodsTopicModule(pageNumber, pageSize);
    }

    @PutMapping("/goodsTopic/module/del/{id}")
    @ApiOperation(value = "删除模快")
    public Result<GoodsTopicModuleVO> delGoodsTopicModule(@PathVariable Long id) {
        return goodsTopicService.delGoodsTopicModule(id);
    }
}
