package com.h9.api.controller;

import com.h9.api.model.vo.AnnouncementVO;
import com.h9.api.service.AnnouncementServise;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Announcement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * Created by 李圆
 * on 2017/11/20
 */
@Api( description = "公告详情")
@RestController
public class AnnouncementController {
    @Resource
    AnnouncementServise announcementServise;

    @ApiOperation(value = "获取公告内容")
    @GetMapping(value = "/account/noticeDetail/{id}")
    public Result<AnnouncementVO> finOne(@NotNull(message="请输入公告标识")@PathVariable("id")Long id){
        return  announcementServise.findOne(id);
    }
}
