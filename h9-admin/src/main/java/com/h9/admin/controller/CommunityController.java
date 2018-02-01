package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.activity.ActivityAddDTO;
import com.h9.admin.model.dto.activity.ActivityEditDTO;
import com.h9.admin.model.dto.community.*;
import com.h9.admin.service.CommunityService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.config.Announcement;
import com.h9.common.db.entity.config.Banner;
import com.h9.common.db.entity.config.BannerType;
import com.h9.common.db.entity.lottery.Activity;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.modle.vo.Config;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: George
 * @date: 2017/11/1 17:07
 */
@RestController
@Api(description = "社区管理")
@RequestMapping(value = "/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Secured
    @GetMapping(value="/banner_type/location")
    @ApiOperation("获取功能类别所有位置")
    public Result<List<Config>> listBannerTypeLocation(){
        return this.communityService.listBannerTypeLocation();
    }

    @Secured
    @GetMapping(value="/banner_type/type")
    @ApiOperation("获取功能类别所有位置")
    public Result<List<Config>> listBannerType(){
        return this.communityService.listBannerType();
    }

    @Secured(accessCode = "banner_type:add")
    @PostMapping(value="/banner_type")
    @ApiOperation("增加功能类别")
    public Result<BannerType> addBannerType(@Validated @RequestBody BannerTypeAddDTO bannerTypeDTO){
        return this.communityService.addBannerType(bannerTypeDTO.toBannerType());
    }

    @Secured(accessCode = "banner_type:update")
    @PutMapping(value="/banner_type")
    @ApiOperation("编辑功能类别")
    public Result<BannerType> updateBannerType(@Validated @RequestBody BannerTypeEditDTO bannerTypeDTO){
        return this.communityService.updateBannerType(bannerTypeDTO);
    }

    @Secured(accessCode = "banner_type:list")
    @GetMapping(value="/banner_type/page")
    @ApiOperation("分页获取功能类别")
    public Result<PageResult<BannerType>> getBannerTypes(BannerTypeListDTO pageDTO){
        return this.communityService.getBannerTypes(pageDTO);
    }

    @Secured(accessCode = "banner_type:status:change")
    @PutMapping(value="/banner_type/status/{id}")
    @ApiOperation("禁用/开启功能类别")
    public Result<BannerType> updateBannerTypeStatus(@ApiParam(value = "功能分类id")@PathVariable long id){
        return this.communityService.updateBannerTypeStatus(id);
    }

    @Secured(accessCode = "banner:add")
    @PostMapping(value="/banner")
    @ApiOperation("增加功能")
    public Result<Banner> addBanner(@Validated @RequestBody BannerAddDTO bannerAddDTO){
        return this.communityService.addBanner(bannerAddDTO);
    }

    @Secured(accessCode = "banner:update")
    @PutMapping(value="/banner")
    @ApiOperation("编辑功能")
    public Result<Banner> updateBanner(@Validated @RequestBody BannerEditDTO bannerEditDTO){
        return this.communityService.updateBanner(bannerEditDTO);
    }

    @Secured(accessCode = "banner:list")
    @GetMapping(value="/banner/page/{bannerTypeId}")
    @ApiOperation("分页获取功能")
    public Result<PageResult<Banner>> getBanners(@PathVariable long bannerTypeId, PageDTO pageDTO){
        return this.communityService.getBanners(bannerTypeId,pageDTO);
    }

    @Secured(accessCode = "banner:delete")
    @DeleteMapping(value="/banner/{id}")
    @ApiOperation("删除功能")
    public Result deleteBanner(@PathVariable long id){
        return this.communityService.deleteBanner(id);
    }

    @Secured(accessCode = "activity:add")
    @PostMapping(value="/activity")
    @ApiOperation("增加活动")
    public Result<Activity> addActivity(@Validated @RequestBody ActivityAddDTO activityAddDTO){
        return this.communityService.addActivity(activityAddDTO.toActivity());
    }

    @Secured(accessCode = "activity:update")
    @PutMapping(value="/activity")
    @ApiOperation("编辑活动")
    public Result<Activity> editActivity(@Validated @RequestBody ActivityEditDTO activityEditDTO){
        return this.communityService.updateActivity(activityEditDTO);
    }

    @Secured(accessCode = "activity:list")
    @GetMapping(value="/activity/page")
    @ApiOperation("分页获取活动")
    public Result<PageResult<Activity>> getActivities(PageDTO pageDTO){
        return this.communityService.getActivities(pageDTO);
    }

    @Secured(accessCode = "activity:status:change")
    @PutMapping(value="/activity/{id}/status")
    @ApiOperation("开启/关闭活动")
    public Result<Activity> editActivityStatus(@PathVariable long id){
        return this.communityService.updateActivityStatus(id);
    }

    @Secured(accessCode = "goods:add")
    @PostMapping(value="/goods")
    @ApiOperation("增加商品")
    public Result<Goods> addGoods(@Validated @RequestBody GoodsAddDTO goodsAddDTO){
        return this.communityService.addGoods(goodsAddDTO);
    }

    @Secured(accessCode = "goods:update")
    @PutMapping(value="/goods")
    @ApiOperation("编辑商品")
    public Result<Goods> addGoods(@Validated @RequestBody GoodsEditDTO goodsEditDTO){
        return this.communityService.updateGoods(goodsEditDTO);
    }

    @Secured(accessCode = "goods:list")
    @GetMapping(value="/goods/page")
    @ApiOperation("分页获取商品")
    public Result<PageResult<Goods>> getGoods(PageDTO pageDTO){
        return this.communityService.getGoods(pageDTO);
    }

    @Secured(accessCode = "goods:status:change")
    @PutMapping(value="/goods/{id}/status")
    @ApiOperation("上架/下架商品")
    public Result<Goods> addGoods(@PathVariable long id){
        return this.communityService.updateGoodsStatus(id);
    }

    @Secured(accessCode = "announcement:add")
    @PostMapping(value="/announcement")
    @ApiOperation("增加公告")
    public Result<Announcement> addAnnouncement(@Validated @RequestBody AnnouncementAddDTO announcementAddDTO){
        return this.communityService.addAnnouncement(announcementAddDTO.toAnnouncement());
    }

    @Secured(accessCode = "announcement:update")
    @PutMapping(value="/announcement")
    @ApiOperation("编辑公告")
    public Result<Announcement> editAnnouncement(@Validated @RequestBody AnnouncementEditDTO announcementEditDTO){
        return this.communityService.updateAnnouncement(announcementEditDTO);
    }

    @Secured(accessCode = "announcement:status:change")
    @PutMapping(value="/announcement/{id}/status")
    @ApiOperation("启用/禁用公告")
    public Result<Announcement> editAnnouncementStatus(@PathVariable long id){
        return this.communityService.updateAnnouncementStatus(id);
    }

    @Secured(accessCode = "announcement:delete")
    @DeleteMapping(value="/announcement/{id}")
    @ApiOperation("删除公告")
    public Result deleteAnnouncement(@PathVariable long id){
        return this.communityService.deleteAnnouncement(id);
    }

    @Secured(accessCode = "announcement:list")
    @GetMapping(value="/announcement/page")
    @ApiOperation("分页获取公告")
    public Result<PageResult<Announcement>> getAnnouncements(PageDTO pageDTO){
        return this.communityService.getAnnouncements(pageDTO);
    }

}
