package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.*;
import com.h9.admin.model.dto.activity.ActivityAddDTO;
import com.h9.admin.model.dto.activity.ActivityEditDTO;
import com.h9.admin.model.dto.community.BannerAddDTO;
import com.h9.admin.model.dto.community.BannerEditDTO;
import com.h9.admin.model.dto.community.BannerTypeAddDTO;
import com.h9.admin.model.dto.community.BannerTypeEditDTO;
import com.h9.admin.service.CommunityService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Activity;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("增加功能类别")
    public Result<BannerType> addBannerType(@Validated @RequestBody BannerTypeAddDTO bannerTypeDTO){
        return this.communityService.addBannerType(bannerTypeDTO.toBannerType());
    }

    @Secured
    @PutMapping(value="/banner_type")
    @ApiOperation("编辑功能类别")
    public Result<BannerType> updateBannerType(@Validated @RequestBody BannerTypeEditDTO bannerTypeDTO){
        return this.communityService.updateBannerType(bannerTypeDTO);
    }

    @Secured
    @GetMapping(value="/banner_type/page")
    @ApiOperation("分页获取功能类别")
    public Result<PageResult<BannerType>> getBannerTypes(PageDTO pageDTO){
        return this.communityService.getBannerTypes(pageDTO);
    }

    @Secured
    @PutMapping(value="/banner_type/status/{id}")
    @ApiOperation("禁用/开启功能类别")
    public Result<BannerType> updateBannerTypeStatus(@ApiParam(value = "功能分类id")@PathVariable long id){
        return this.communityService.updateBannerTypeStatus(id);
    }

    @Secured
    @PostMapping(value="/banner")
    @ApiOperation("增加功能")
    public Result<Banner> addBanner(@Validated @RequestBody BannerAddDTO bannerAddDTO){
        return this.communityService.addBanner(bannerAddDTO);
    }

    @Secured
    @PutMapping(value="/banner")
    @ApiOperation("编辑功能")
    public Result<Banner> updateBanner(@Validated @RequestBody BannerEditDTO bannerEditDTO){
        return this.communityService.updateBanner(bannerEditDTO);
    }

    @Secured
    @GetMapping(value="/banner/page/{banner_type_id}")
    @ApiOperation("分页获取功能")
    public Result<PageResult<Banner>> getBanners(@PathVariable long banner_type_id, PageDTO pageDTO){
        return this.communityService.getBanners(banner_type_id,pageDTO);
    }

    @Secured
    @DeleteMapping(value="/banner/{id}")
    @ApiOperation("删除功能")
    public Result deleteBanner(@PathVariable long id){
        return this.communityService.deleteBanner(id);
    }

    @Secured
    @PostMapping(value="/activity")
    @ApiOperation("增加活动")
    public Result<Activity> addActivity(@Validated @RequestBody ActivityAddDTO activityAddDTO){
        return this.communityService.addActivity(activityAddDTO.toActivity());
    }

    @Secured
    @PutMapping(value="/activity")
    @ApiOperation("编辑活动")
    public Result<Activity> editActivity(@Validated @RequestBody ActivityEditDTO activityEditDTO){
        return this.communityService.updateActivity(activityEditDTO);
    }

    @Secured
    @GetMapping(value="/activity/page")
    @ApiOperation("分页获取活动")
    public Result<PageResult<Activity>> getActivities(PageDTO pageDTO){
        return this.communityService.getActivities(pageDTO);
    }



}
