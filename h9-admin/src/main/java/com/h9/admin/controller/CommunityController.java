package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.BannerTypeAddDTO;
import com.h9.admin.model.dto.BannerTypeEditDTO;
import com.h9.admin.model.dto.PageDTO;
import com.h9.admin.service.CommunityService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        return this.communityService.editBannerType(bannerTypeDTO);
    }

    @Secured
    @GetMapping(value="/banner_type/page")
    @ApiOperation("分页获取功能类别")
    public Result<PageResult<BannerType>> getBannerTypes(PageDTO pageDTO){
        return this.communityService.getBannerTypes(pageDTO);
    }
}
