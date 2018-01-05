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

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '活动名称'")
    private String name;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '活动内容'")
    private String content;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '图片'")
    private String image;

    @Column(name = "stick_count",nullable = false,columnDefinition = "int default 0 COMMENT ''")
    private Integer stickCount;

    @Column(name = "read_count",nullable = false,columnDefinition = "int default 0 COMMENT '阅读数量'")
    private Integer readCount;

    @Column(name = "answer_count",nullable = false,columnDefinition = "int default 0 COMMENT '回答数量'")
    private Integer answerCount;






}
