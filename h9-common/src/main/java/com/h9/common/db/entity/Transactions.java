package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * description: 转账记录
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transactions extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "user_id",columnDefinition = "bigint comment '发起转账的用户'")
    private Long userId;



    @Column(name = "target_user_id",columnDefinition = "bigint comment '转账接受方用户'")
    private Long targetUserId;

    @Column(name = "transfer_money",columnDefinition = "decimal(10,2) comment '转账金额'")
    private BigDecimal transferMoney;

    @Column(name = "remarks",columnDefinition = "varchar(500) comment '备注'")
    private String remarks;

    @Column(name = "balance_flow_type",columnDefinition = "varchar(20) comment '流水类型'")
    private Long balanceFlowType;

    @Column(name = "temp_id",columnDefinition = "varchar(200) comment 'tempId'")
    private String tempId;

    @Column(name="phone",columnDefinition = "varchar(200) comment '发起人手机号码'")
    private String phone;

    @Column(name="target_phone",columnDefinition = "varchar(200) comment '接受方手机号码'")
    private String targetPhone;

    @Column(name = "nickName",columnDefinition = "varchar(500) comment '发起人妮称'")
    private String nickName;

    @Column(name = "target_nick_name",columnDefinition = "varchar(500) comment '接受方妮称'")
    private String targetNickName;
}
