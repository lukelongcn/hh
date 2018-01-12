package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "stick_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '帖子'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Stick stick;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '内容'")
    private String content;
}
