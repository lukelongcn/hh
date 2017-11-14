package com.h9.admin.model.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author: George
 * @date: 2017/11/14 16:16
 */
public class AnnouncementVO {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


    @Column(name = "title", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '标题'")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(512) default '' COMMENT '内容'")
    private String content;

    @Column(name = "url", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '跳转链接'")
    private String url;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '用户名'")
    private String userName;

    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 启用 0禁用 2删除'")
    private Integer enable;

    @Temporal(TIMESTAMP)
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '开始时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '结束时间'")
    private Date endTime;

    @Column(name = "sort",nullable = false,columnDefinition = "tinyint default 1 COMMENT '排序'")
    private Integer sort = 1;

    @Column(name = "img_url", columnDefinition = "varchar(200) COMMENT '文章显示图片'")
    private String imgUrl;
}
