package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 18:13
 */
@Entity
@Table(name = "bannerType")
public class BannerType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型名称'")
    private String name;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '界面显示标题'")
    private String title;

    @Column(name = "icon", columnDefinition = "varchar(128) default '' COMMENT '类型图标'")
    private String icon;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型标识'")
    private String code;

    @Column(name = "url", columnDefinition = "varchar(256) default '' COMMENT '跳转链接'")
    private String url;

    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用'")
    private Integer enable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }


}
