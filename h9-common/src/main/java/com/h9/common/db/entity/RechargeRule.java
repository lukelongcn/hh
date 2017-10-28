package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:充值规则
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 14:14
 */

@Entity
@Table(name = "recharge_rule")
public class RechargeRule extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;
    
    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '充值显示名称'")
    private String name;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '充值金额'")
    private BigDecimal money = new BigDecimal(0);
    
    @Column(name = "need_pay",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '需要支付金额'")
    private BigDecimal needPay = new BigDecimal(0);
    
    @Column(name = "cash_back",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励金额'")
    private BigDecimal cashBack = new BigDecimal(0);

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 启用 2 禁用'")
    private Integer status = 1;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getNeedPay() {
        return needPay;
    }

    public void setNeedPay(BigDecimal needPay) {
        this.needPay = needPay;
    }

    public BigDecimal getCashBack() {
        return cashBack;
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
