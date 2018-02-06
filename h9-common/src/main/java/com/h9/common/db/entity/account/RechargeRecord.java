package com.h9.common.db.entity.account;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/11/18.
 */
@Entity
@Table(name = "recharge_record")
public class RechargeRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recharge_id",columnDefinition = "varchar(255) comment 'h9商户对第三方充值订单的id,UUID'")
    private String rechargeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "money", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '充值金额'")
    private BigDecimal money;

    @Column(name = "user_name", columnDefinition = "varchar(50) COMMENT '用户名'")
    private String userName;

    @Column(name = "tel", columnDefinition = "varchar(50) COMMENT '充值的手机号码'")
    private String tel;

    /**
     * description: order id
     */
    @Column(name = "order_id")
    private Long orderId;



    public RechargeRecord(Long userId, BigDecimal money, String userName, String tel,String rechargeId,Long orderId) {
        this.userId = userId;
        this.money = money;
        this.userName = userName;
        this.tel = tel;
        this.orderId = orderId;
        this.rechargeId = rechargeId;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public RechargeRecord( ) {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
