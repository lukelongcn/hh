package com.h9.common.db.entity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by itservice on 2017/11/6.
 */
@Entity
@Table(name = "goods_didi_number")
public class GoodsDIDINumber {

    @Id
    @SequenceGenerator(name = "h9-apiSeq")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "didi_number")
    private String didiNumber;
    @Column(name="goods_id")
    private Long goodsId;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "status",columnDefinition = "int COMMENT '1为正常，2为已兑换'")
    private Integer status;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDidiNumber() {
        return didiNumber;
    }

    public void setDidiNumber(String didiNumber) {
        this.didiNumber = didiNumber;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
