package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description: 收货地址
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 15:04
 */

@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "old_ID")
    private Long oldId;
    @Column(name = "Userid")

    private Long Userid;
    @Column(name = "Consignee")

    private String Consignee;
    @Column(name = "ConsigneePhone")

    private String ConsigneePhone;
    @Column(name = "Receivingaddress")

    private String Receivingaddress;
    @Column(name = "ADefault")

    private Integer ADefault;
    @Column(name = "Province")

    private String Province;
    @Column(name = "City")

    private String City;

    @Column(name = "District")
    private String District;

    @Column(name = "ProvincialCity")
    private String ProvincialCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public Long getUserid() {
        return Userid;
    }

    public void setUserid(Long userid) {
        Userid = userid;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getConsigneePhone() {
        return ConsigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        ConsigneePhone = consigneePhone;
    }

    public String getReceivingaddress() {
        return Receivingaddress;
    }

    public void setReceivingaddress(String receivingaddress) {
        Receivingaddress = receivingaddress;
    }

    public Integer getADefault() {
        return ADefault;
    }

    public void setADefault(Integer ADefault) {
        this.ADefault = ADefault;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getProvincialCity() {
        return ProvincialCity;
    }

    public void setProvincialCity(String provincialCity) {
        ProvincialCity = provincialCity;
    }


}
