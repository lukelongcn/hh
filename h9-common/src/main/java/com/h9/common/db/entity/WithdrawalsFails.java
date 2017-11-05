package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;


/**
 * description: 提现失败表
 */
@Entity
@Table(name = "withdrawals_record")
public class WithdrawalsFails extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name="status",columnDefinition = "int default 0 COMMENT'处理状态,1为未处理 2为处理完成'")
    private Integer status;

    @Column(name="bank_return_data",nullable = false,columnDefinition = "varchar(255) default '' COMMENT '银行返回的数据' ")
    private String bankReturnData;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现金额'")
    private BigDecimal money = new BigDecimal(0);
}
