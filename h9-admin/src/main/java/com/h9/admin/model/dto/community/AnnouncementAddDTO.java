package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.config.Announcement;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/14 16:17
 */
public class AnnouncementAddDTO {

    @ApiModelProperty(value = "标题",required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容",required = true)
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "跳转链接",required = true)
//    @NotBlank(message = "跳转链接不能为空")
    private String url;

    @ApiModelProperty(value = "状态：1：启用，0：禁用",required = true)
    @NotNull(message = "状态不能为空")
    private Integer enable;

    @ApiModelProperty(value = "发布时间",required = true)
    private Date publishTime;

    @ApiModelProperty(value = "排序",required = true)
//    @NotNull(message = "排序不能为空")
    private Integer sort = 1;

    @ApiModelProperty(value = "文章图片",required = true)
//    @NotBlank(message = "文章图片不能为空")
    private String imgUrl;

    @ApiModelProperty(value = "作者",required = true)
//    @NotBlank(message = "作者不能为空")
    private String userName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = new Date(publishTime);
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort==null?1:sort;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Announcement toAnnouncement(){
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(this,announcement);
        return  announcement;
    }
}
