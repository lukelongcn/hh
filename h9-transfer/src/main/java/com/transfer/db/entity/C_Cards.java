package com.transfer.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description: 滴滴兑换关系表
 */
@Getter
@Setter
@ToString
//@Entity
//@Table(name = "C_Cards")
public class C_Cards {

    private Integer CardID;
    private String CardName;
    private Date StartDate;
    private Date EndDate;
    private Integer Type;
    private String Information;
    private Integer UserID;
    private Integer Equal;
    private Integer Activity_FK_id;
    private Integer GoodsInfoid;
    private String CardPicture;
    private Integer Source;
    private String GoodsContent;
    private BigDecimal CardPrice;
    private String CardCode;
    private String explain;
    private Integer IsnotReceive;
    private Integer IsnotRead;
}
