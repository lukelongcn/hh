package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/1
 * Time: 18:36
 */
@Getter
@Setter
@Entity
@Table(name = "oratrans")
public class Oratrans {


    @Id
    @Column(name = "transDate")
    private String transDate;

    private String orderNo;

    private String flag;

    private String cardNo;

    private String usrName;

    private String prov;

    private String city;

    private String bakName;

    private String subBank;

    private BigDecimal transAmt = new BigDecimal(0);
    @Column(name = "FinState")
    private int finState;

    @Column(name = "OratransOId")
    private String oratransOId;
    @Column(name = "OratransTime")
    private Date oratransTime;
    @Column(name = "QueryCount")
    private int queryCount;






}
