package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import sun.awt.image.SunWritableRaster;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/24
 * Time: 18:20
 */

@Getter
@Setter
@Entity
@Table(name = "bounsDetails")
public class BounsDetails {


    @Id
    @Column(name="BounsDetailsOID")
    private String BounsDetailsOID;

    @Column(name="BounsOID")
    private String BounsOID;

    @Column(name="userid")
    private Long userid;

    @Column(name="username")
    private String username;

    @Column(name="userBouns")
    private BigDecimal userBouns;

    @Column(name="State")
    private Long State;

    @Column(name="BounsTime")
    private Date BounsTime;

    @Column(name="BounsType")
    private Long BounsType;

    @Column(name="ToUid")
    private Long ToUid;

    @Column(name="OratransOId")
    private String OratransOId;

    @Column(name="TransState")
    private Long TransState;

    @Column(name="clickCount")
    private Long clickCount;

    @Column(name="Imei")
    private String Imei;

    @Column(name="Os")
    private String Os;


    @Column(name="Manufacturer")
    private String Manufacturer;

    @Column(name="Model")
    private String Model;

    @Column(name="Version")
    private String Version;

    @Column(name="liquorid")
    private Long liquorid;

    @Column(name="IntegralPoints")
    private BigDecimal IntegralPoints;

    @Column(name="CollectionTime")
    private Date CollectionTime;

}
