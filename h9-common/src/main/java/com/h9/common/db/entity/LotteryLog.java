package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/3
 * Time: 15:53
 */

@Entity
@Table(name = "lotteryLog")
public class LotteryLog extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户id'")
    private Long userId;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '奖励条码'")
    private String code;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_record_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    private UserRecord userRecord;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
