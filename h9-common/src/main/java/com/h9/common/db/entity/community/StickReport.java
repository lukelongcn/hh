package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2018/1/12
 */
@Data
@Entity
@Table(name = "stick_report")
public class StickReport  extends BaseEntity {
    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户'")
    private Long userId;

    @Column(name = "stick_id", columnDefinition = "bigint(20) default null COMMENT '帖子id'")
    private Long stickId;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '内容'")
    private String content;
}
