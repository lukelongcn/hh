package com.h9.api.controller;

import com.h9.api.service.BigRichService;
import com.h9.common.base.Result;
import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:一号大富贵活动</p>
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
@RestController
@RequestMapping("/bigrich")
public class BigRichController {

    @Resource
    private BigRichService bigRichServicel;

    /** TODO */
    @GetMapping("/record")
    public Result getRecord(@SessionAttribute("curUserId")long userId){
        return bigRichServicel.getRecord(userId);
    }

}
