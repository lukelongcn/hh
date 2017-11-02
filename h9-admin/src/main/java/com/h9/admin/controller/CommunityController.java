package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.service.CommunityService;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/1 17:07
 */
@RestController
@Api(description = "社区")
@RequestMapping(value = "/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Secured
    @PostMapping(value="/banner_type")
    @ApiOperation("增加功能类型")
    public Result<BannerType> add(@Validated @ModelAttribute BannerType bannerType){
        return this.communityService.addBannerType(bannerType);
    }

    /*@InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new Si));
    }*/
}
