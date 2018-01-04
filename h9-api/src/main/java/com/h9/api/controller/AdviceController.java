package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.AdviceDTO;
import com.h9.api.service.AdviceService;
import com.h9.common.base.Result;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by 李圆 on 2018/1/3
 */
@RestController
@RequestMapping("/advice")
public class AdviceController {
    @Resource
    AdviceService adviceService;

    /**
     * 获取意见反馈类别
     * @return
     */
    @GetMapping("/adviceType")
    public Result adviceType(){
        return adviceService.getAdviceType();
    }

    /**
     * 提交意见反馈信息
     * @param userId
     * @param adviceDTO
     * @return
     */
    @Secured
    @PostMapping("/sendAdvice")
    @Description("提交意见反馈信息")
    public Result sendAdvice(@SessionAttribute("curUserId")long userId, @Valid @RequestBody AdviceDTO adviceDTO,HttpServletRequest request){
         return  adviceService.sendAdvice(userId,adviceDTO,request);
    }
}
