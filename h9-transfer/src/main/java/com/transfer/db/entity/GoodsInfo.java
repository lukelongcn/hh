package com.transfer.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by itservice on 2017/11/15.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "GoodsInfo")
public class GoodsInfo {

    @Id
    @Column(name = "Goodsid")
    private Long Goodsid;

    @Column(name ="Picture_URL")
    private String Picture_URL;

    @Column(name ="Goodsname")
    private String Goodsname;

    @Column(name ="GoodsRemark")
    private String GoodsRemark;

    @Column(name ="GoodsType")
    private Integer GoodsType;

    @Column(name ="StartTime")
    private Date StartTime;

    @Column(name ="Endtime")
    private Date Endtime;

    @Column(name ="GoodsText")
    private String GoodsText;

    @Column(name ="GoodsState")
    private Integer GoodsState;

    @Column(name ="IntegralCount")
    private Integer IntegralCount;

    @Column(name ="IdCode")
    private String IdCode;

    @Column(name ="Price")
    private BigDecimal Price;
}
