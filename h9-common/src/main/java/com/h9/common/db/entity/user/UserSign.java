package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2017/12/29
 */
@Entity
@Table(name = "user_sign")
public class UserSign  extends BaseEntity {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint(20) default 0 COMMENT '用户id'")
    private Long userId;

    @Column(name = "cash_back",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '签到奖励金额'")
    private BigDecimal cashBack = new BigDecimal(0);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getCashBack() {
        return cashBack;
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }


}
