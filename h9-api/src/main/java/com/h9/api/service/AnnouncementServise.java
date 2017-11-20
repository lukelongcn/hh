package com.h9.api.service;

import com.h9.api.model.vo.AnnouncementVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Announcement;
import com.h9.common.db.repo.AnnouncementRepository;
import com.h9.common.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by 李圆
 * on 2017/11/20
 */
@Service
@Transactional
public class AnnouncementServise {

    @Resource
    AnnouncementRepository announcementRepository;

    @ApiOperation(value = "获取公告内容")
    public Result findOne(Long id){

        Announcement announcement =  announcementRepository.findOne(id);
        if(announcement == null){
            return Result.fail("公告不存在");
        }
        AnnouncementVO announcementVO = new AnnouncementVO(announcement);
        announcementVO.setCreateTime(DateUtil.formatDate(announcement.getCreateTime(), DateUtil.FormatType.DAY));
        return Result.success(announcementVO);
    }
}
