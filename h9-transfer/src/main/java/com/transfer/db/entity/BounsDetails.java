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
//
//@Getter
//@Setter
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
    private Long bounsType;

    @Column(name="ToUid")
    private Long ToUid;

    @Column(name="OratransOId")
    private String oratransOId;

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

    public String getBounsDetailsOID() {
        return BounsDetailsOID;
    }

    public void setBounsDetailsOID(String bounsDetailsOID) {
        BounsDetailsOID = bounsDetailsOID;
    }

    public String getBounsOID() {
        return BounsOID;
    }

    public void setBounsOID(String bounsOID) {
        BounsOID = bounsOID;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getUserBouns() {
        return userBouns;
    }

    public void setUserBouns(BigDecimal userBouns) {
        this.userBouns = userBouns;
    }

    public Long getState() {
        return State;
    }

    public void setState(Long state) {
        State = state;
    }

    public Date getBounsTime() {
        return BounsTime;
    }

    public void setBounsTime(Date bounsTime) {
        BounsTime = bounsTime;
    }

    public Long getBounsType() {
        return bounsType;
    }

    public void setBounsType(Long bounsType) {
        this.bounsType = bounsType;
    }

    public Long getToUid() {
        return ToUid;
    }

    public void setToUid(Long toUid) {
        ToUid = toUid;
    }

    public String getOratransOId() {
        return oratransOId;
    }

    public void setOratransOId(String oratransOId) {
        this.oratransOId = oratransOId;
    }

    public Long getTransState() {
        return TransState;
    }

    public void setTransState(Long transState) {
        TransState = transState;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public String getOs() {
        return Os;
    }

    public void setOs(String os) {
        Os = os;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public Long getLiquorid() {
        return liquorid;
    }

    public void setLiquorid(Long liquorid) {
        this.liquorid = liquorid;
    }

    public BigDecimal getIntegralPoints() {
        return IntegralPoints;
    }

    public void setIntegralPoints(BigDecimal integralPoints) {
        IntegralPoints = integralPoints;
    }

    public Date getCollectionTime() {
        return CollectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        CollectionTime = collectionTime;
    }
}
