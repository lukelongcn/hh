package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 17:37
 */

@Entity
@Table(name = "flowType")
public class FlowType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "flow_name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '流水类型名称'")
    private String flowName;

    @Column(name = "balance_type",nullable = false,columnDefinition = "int default 0 COMMENT '余额类型：0 余额 1V 币'")
    private Integer balanceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
