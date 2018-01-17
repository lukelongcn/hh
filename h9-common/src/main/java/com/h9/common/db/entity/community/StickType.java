package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 11:14
 */

@Data
@Entity
@Table(name = "stick_type")
public class StickType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '分类名称'")
    private String name;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '板块介绍'")
    private String content;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '板块图片'")
    private String image;

    @Column(name = "stick_count",nullable = false,columnDefinition = "int default 0 COMMENT '贴子数量'")
    private Integer stickCount;

    @Column(name = "limit_state",nullable = false,columnDefinition = "int default 1 COMMENT '限制发帖 1不限制 2限制'")
    private Integer limitState = 1;

    @Column(name = "examine_state",nullable = false,columnDefinition = "int default 1 COMMENT '发帖后台审核 1是 2否'")
    private Integer examineState = 1;

    @Column(name = "comment_state",nullable = false,columnDefinition = "int default 1 COMMENT '评论审核 1是 2否'")
    private Integer commentState = 1;

    @Column(name = "admit_state",nullable = false,columnDefinition = "int default 1 COMMENT '是否允许评论 1是 2否'")
    private Integer admitsState = 1;

    @Column(name = "sort", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '顺序'")
    private String sort;





}
