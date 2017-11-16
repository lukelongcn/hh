package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.Announcement;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/14 16:37
 */
public class AnnouncementEditDTO extends AnnouncementAddDTO{
    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Announcement toAnnouncement(Announcement announcement){
        BeanUtils.copyProperties(this,announcement);
        return  announcement;
    }
}
