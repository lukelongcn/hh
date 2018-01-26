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

}
