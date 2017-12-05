package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 19:42
 */

@Entity
@Table(name = "GoodsOrder")
public class GoodsOrder extends BaseEntity {


    @Id
    @Column(name="GoodsOrderId")
    private Long id;

    @Column(name="GoodsIntegral")
    private BigDecimal goodsIntegral;

    @Column(name="GoodsName")
    private BigDecimal goodsName;

    @Column(name="OrderState")
    private BigDecimal orderState;


    @Column(name="OrderTime")
    private String orderTime;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGoodsIntegral() {
        return goodsIntegral;
    }

    public void setGoodsIntegral(BigDecimal goodsIntegral) {
        this.goodsIntegral = goodsIntegral;
    }

    public BigDecimal getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(BigDecimal goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getOrderState() {
        return orderState;
    }

    public void setOrderState(BigDecimal orderState) {
        this.orderState = orderState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
