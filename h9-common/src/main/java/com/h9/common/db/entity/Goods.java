package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:商品，包括虚拟商品
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 11:33
 */

@Entity
@Table(name = "goods")
public class Goods extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '充值显示名称'")
    private String name;

    @Column(name = "real_price",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '虚拟商品价值'")
    private BigDecimal realPrice = new BigDecimal(0);

    @Column(name = "price",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '商品价格'")
    private BigDecimal price = new BigDecimal(0);

    @Column(name = "cash_back",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励金额'")
    private BigDecimal cashBack = new BigDecimal(0);

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 上架 2 下架'")
    private Integer status = 1;

    @Column(name = "description", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '描述'")
    private String description;

    @Column(name = "stock",nullable = false,columnDefinition = "tinyint default 0 COMMENT '库存'")
    private Integer stock = 0;

//    @Column(name = "goods_type",nullable = false,columnDefinition = "int default 1 COMMENT '类别（1，为手机充值 2，为 滴滴卡兑换）'")
    @ManyToOne()
    @JoinColumn(name = "goods_type_id",nullable = false,referencedColumnName="id")
    private GoodsType goodsType;

    public Integer getStock() {
        return stock;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

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

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
