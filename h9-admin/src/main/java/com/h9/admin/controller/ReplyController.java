package com.h9.admin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.h9.admin.model.dto.ReplyDTO;
import com.h9.admin.model.dto.WXMatterDTO;
import com.h9.admin.model.dto.WXReplySearchDTO;
import com.h9.admin.model.vo.ReplyMessageVO;
import com.h9.admin.service.ReplyService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by 李圆 on 2018/1/15
 */
@RestController
@Api(description="微信回复规则操作")
@RequestMapping("/wx/reply")
public class ReplyController {


     org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(ReplyController.class);
    @Resource
    private ReplyService replyService;

    /**
     * 添加微信回复规则
     */
    @ApiOperation("添加微信回复规则")
    @PostMapping("/add")
    public Result addRule(@Valid@RequestBody ReplyDTO replyDTO){
        return replyService.addRule(replyDTO);
    }


    /**
     * 禁用或启用
     */
    @ApiOperation("禁用微信回复规则")
    @PostMapping("/disable/{id}")
    public Result disable(@PathVariable long id){
        return replyService.disable(id);
    }

    /**
     * 获取单个详情
     */
    @ApiOperation("获取单个微信回复规则详情")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable long id){
        return replyService.detail(id);
    }

    /**
     * 获取规则列表
     */
    @ApiOperation("获取微信回复规则列表")
    @GetMapping("/all")
    public Result all(@RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "10") Integer limit){
        return replyService.all(page,limit);
    }

    /**
     * 编辑
     */
    @ApiOperation("编辑微信回复规则")
    @PostMapping("update/{id}")
    public Result update(@PathVariable long id,@Valid@RequestBody ReplyDTO replyDTO){
        return replyService.update(id,replyDTO);
    }

    /**
     * 微信回复规则类型
     */
    @ApiOperation("微信回复规则类型")
    @GetMapping("/ruleType")
    public Result ruleType(){
        return replyService.getRuleType();
    }

    /**
     * 微信回复类型
     */
    @ApiOperation("微信回复类型")
    @GetMapping("/replyType")
    public Result getReplyType(){
        return replyService.getReplyType();
    }

    @PostMapping("/list")
    @ApiOperation("搜索微信回复规则")
    public Result<PageResult<ReplyMessageVO>> wxOrderDetail(@RequestBody WXReplySearchDTO wxReplySearchDTO){
        return replyService.replyMessageList(wxReplySearchDTO);
    }



    @ApiOperation("拿到对应类型素材列表")
    @PostMapping("/matter")
    public Result getMatter(@RequestBody WXMatterDTO wxMatterDTO,
                            @RequestParam(defaultValue = "1") Integer page){
        if (page<=1){
            wxMatterDTO.setOffset(0);
        }else {
            wxMatterDTO.setOffset(page*wxMatterDTO.getCount()-1);
        }
        String access_token = replyService.getWeChatAccessToken();
        logger.debug(access_token);
        RestTemplate restTemplate = new RestTemplate();
        String wXmatterVO =  restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="
               +access_token,wxMatterDTO,String.class);
        JSONObject jsonObject = JSON.parseObject(wXmatterVO);
        return Result.success(jsonObject);
    }



}
