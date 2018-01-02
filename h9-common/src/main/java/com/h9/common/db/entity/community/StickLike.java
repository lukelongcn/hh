package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 16:21
 */

@Entity
@Table(name = "stick_like")
public class StickLike extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "stick_id", columnDefinition = "bigint(20) default null COMMENT '帖子id'")
    private Long stick_id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '点赞用户'")
    private Long user_id;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '点赞状态'")
    private Integer status = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStick_id() {
        return stick_id;
    }

    public void setStick_id(Long stick_id) {
        this.stick_id = stick_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
