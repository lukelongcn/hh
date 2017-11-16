package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.activity.ActivityAddDTO;
import com.h9.admin.model.dto.activity.ActivityEditDTO;
import com.h9.admin.model.dto.community.*;
import com.h9.admin.service.CommunityService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.modle.dto.PageDTO;
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
    @GetMapping(value="/banner/page/{bannerTypeId}")
    @ApiOperation("分页获取功能")
    public Result<PageResult<Banner>> getBanners(@PathVariable long bannerTypeId, PageDTO pageDTO){
        return this.communityService.getBanners(bannerTypeId,pageDTO);
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

    @Secured
    @PutMapping(value="/activity/{id}/status")
    @ApiOperation("开启/关闭活动")
    public Result<Activity> editActivityStatus(@PathVariable long id){
        return this.communityService.updateActivityStatus(id);
    }

    @Secured
    @PostMapping(value="/goods")
    @ApiOperation("增加商品")
    public Result<Goods> addGoods(@Validated @RequestBody GoodsAddDTO goodsAddDTO){
        return this.communityService.addGoods(goodsAddDTO);
    }

    @Secured
    @PutMapping(value="/goods")
    @ApiOperation("编辑商品")
    public Result<Goods> addGoods(@Validated @RequestBody GoodsEditDTO goodsEditDTO){
        return this.communityService.updateGoods(goodsEditDTO);
    }

    @Secured
    @GetMapping(value="/goods/page")
    @ApiOperation("分页获取商品")
    public Result<PageResult<Goods>> getGoods(PageDTO pageDTO){
        return this.communityService.getGoods(pageDTO);
    }

    @Secured
    @PutMapping(value="/goods/{id}/status")
    @ApiOperation("上架/下架商品")
    public Result<Goods> addGoods(@PathVariable long id){
        return this.communityService.updateGoodsStatus(id);
    }

    @Secured
    @PostMapping(value="/announcement")
    @ApiOperation("增加公告")
    public Result<Announcement> addAnnouncement(@Validated @RequestBody AnnouncementAddDTO announcementAddDTO){
        return this.communityService.addAnnouncement(announcementAddDTO.toAnnouncement());
    }

    @Secured
    @PutMapping(value="/announcement")
    @ApiOperation("编辑公告")
    public Result<Announcement> editAnnouncement(@Validated @RequestBody AnnouncementEditDTO announcementEditDTO){
        return this.communityService.updateAnnouncement(announcementEditDTO);
    }

    @Secured
    @PutMapping(value="/announcement/{id}/status")
    @ApiOperation("启用/禁用公告")
    public Result<Announcement> editAnnouncementStatus(@PathVariable long id){
        return this.communityService.updateAnnouncementStatus(id);
    }

    @Secured
    @GetMapping(value="/announcement/page")
    @ApiOperation("分页获取公告")
    public Result<PageResult<Announcement>> getAnnouncements(PageDTO pageDTO){
        return this.communityService.getAnnouncements(pageDTO);
    }

}
