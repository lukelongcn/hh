package com.h9.api.model.vo;

import com.h9.common.db.entity.config.Announcement;
import org.springframework.beans.BeanUtils;

/**
 * Created by 李圆
 * on 2017/11/20
 */
public class AnnouncementVO {

    private Long id;

    private String title; // 标题

    private String content; // 内容

    private String url; // 跳转链接

    private String userName; // 用户名

    private Integer enable; // 状态 ：1 启用 0禁用 2删除

    protected String createTime ; // 创建时间

    public AnnouncementVO(Announcement announcement){
        BeanUtils.copyProperties(announcement,this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
