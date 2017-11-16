package com.transfer.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description: 滴滴卡劵表
 */
@Getter
@Setter
@ToString
@Table(name = "T_CardCodes")
@Entity
public class T_CardCodes {
    @Id
    private Long ID;
    @Column(name = "CardCode")
    private String CardCode;
    @Column(name = "StartTime")
    private Date StartTime;
    @Column(name = "EndTime")
    private Date EndTime;
    /**
     * description: 0 为兑换  1为未兑换
     */
    @Column(name = "Type")
    private Integer Type;
    @Column(name = "Remarks")
    private String Remarks;
    @Column(name = "Money")
    private BigDecimal Money;
    @Column(name = "IsNotCard")
    private Integer IsNotCard;
}
