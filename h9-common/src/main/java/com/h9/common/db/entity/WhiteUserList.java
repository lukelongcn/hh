package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by itservice on 2017/11/29.
 */
@Entity
@Table(name = "white_user_list")
@Data
@Builder
public class WhiteUserList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '白明单用户'")
    private Long userId;

    @Column(name = "status",nullable = false,columnDefinition = "int default 1 COMMENT '白名单状态 1 正常 2 禁用，失效白名单'")
    private Integer status = 1;

    @Column(name = "cause", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '加入白名单原因'")
    private String cause;

    @Temporal(TIMESTAMP)
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '白名单生效时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '白名单结束时间'")
    private Date endTime;

}
