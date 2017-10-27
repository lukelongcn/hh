package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:余额流水
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 17:30
 */

@Entity
@Table(name = "balanceFlow")
public class BalanceFlow extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "balance",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '变更后的余额'")
    private BigDecimal balance = new BigDecimal(0);

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '变更金额'")
    private BigDecimal money = new BigDecimal(0);

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "flow_type_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '流水类型'")
    private FlowType flowType;

    @Column(name = "balance_type",nullable = false,columnDefinition = "int default 0 COMMENT '金额类型 ： 0 余额 1V 币'")
    private Integer balanceType;

    @Column(name = "remarks", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '资金变动备注'")
    private String remarks;

    @JoinColumn(name = "user_id",columnDefinition = "bigint(20) default 0 COMMENT '资金变更用户'")
    private Long userId;

    @Column(name = "order_id", columnDefinition = "bigint(20) default null COMMENT '订单id'")
    private Long orderId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public Integer getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
