package com.h9.admin.controller;


import com.h9.admin.model.dto.ReplyDTO;
import com.h9.admin.service.ReplyService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * Created by 李圆 on 2018/1/15
 */
@RestController
@RequestMapping("/wx/reply")
public class ReplyController {

    @Resource
    private ReplyService replyService;

    /**
     * 添加微信回复规则
     */
    @PostMapping("/add")
    public Result addRule(@Valid@RequestBody ReplyDTO replyDTO){
        return replyService.addRule(replyDTO);
    }

    /**
     * 微信回复规则类型
     */
    @GetMapping("/ruleType")
    public Result ruleType(){
        return replyService.getRuleType();
    }

    /**
     * 微信回复类型
     */
    @GetMapping("/replyType")
    public Result getReplyType(){
        return replyService.getReplyType();
    }
}
