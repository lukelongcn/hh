package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 16:21
 */
@Data
@Entity
@Table(name = "stick_like")
public class StickLike extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "stick_id", columnDefinition = "bigint(20) default null COMMENT '帖子id'")
    private Long stickId;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '点赞用户'")
    private Long userId;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 0 COMMENT '点赞状态'")
    private Integer status = 0;

}
