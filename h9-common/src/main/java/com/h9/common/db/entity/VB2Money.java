package com.h9.common.db.entity;

/**
 * Created by itservice on 2017/12/5.
 */

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * description: vb转酒元
 */
@Table(name = "vb_to_money")
@Entity
public class VB2Money extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "tel",columnDefinition = "varchar(50) default '' COMMENT 'tel' ")
    private String tel;

    @Column(name = "vb")
    private BigDecimal vb;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "ip",columnDefinition = "varchar(50) default '' COMMENT 'ip'")
    private String ip;

    @Column(name ="user_id")
    private Long userId;

    @Column(name = "user_record_id")
    private Long user_record_id;



    public VB2Money(String tel, BigDecimal vb, BigDecimal money, String ip,Long userId,Long userRecordId) {
        this.tel = tel;
        this.vb = vb;
        this.money = money;
        this.ip = ip;
        this.userId = userId;
        this.user_record_id = userRecordId;
    }

    public Long getUser_record_id() {
        return user_record_id;
    }

    public void setUser_record_id(Long user_record_id) {
        this.user_record_id = user_record_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VB2Money(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public BigDecimal getVb() {
        return vb;
    }

    public void setVb(BigDecimal vb) {
        this.vb = vb;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
