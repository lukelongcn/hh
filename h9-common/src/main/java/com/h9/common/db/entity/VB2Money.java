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
