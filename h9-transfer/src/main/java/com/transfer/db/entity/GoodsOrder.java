package com.transfer.db.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 19:42
 */

@Entity
@Table(name = "GoodsOrder")
public class GoodsOrder{


    @Id
    @Column(name="GoodsOrderId")
    private Integer id;

    @Column(name="GoodsIntegral")
    private Integer goodsIntegral;

    @Column(name="GoodsName")
    private String goodsName;

    @Column(name="OrderState")
    private Integer orderState;

    @Column(name="OrderTime")
    private String orderTime;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "GoodsType")
    private Integer goodsType;

    @Column(name = "OrderAddress")
    private String OrderAddress;

    @Column(name = "OrderPhone")
    private String orderPhone;

    @Column(name = "OrderPerson")
    private String orderPerson;

    @Column(name = "AddressId")
    private Long AddressId;

    @Column(name = "OrderDate")
    private Date OrderDate;

    @Column(name="ExpressName")
    private String ExpressName;

    @Column(name = "ExpressNum")
    private Integer ExpressNum;

    @Column(name="Goodsid")
    private Long goodsId;

    @Column(name = "ProvincialCity")
    private String ProvincialCity;

    @Column(name = "Province")
    private String province;

    @Column(name = "District")
    private String District;

    @Column(name = "City")
    private String city;

    @Column(name = "Type")
    private String type;

    @Column(name = "ExNum")
    private String exNum;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsIntegral() {
        return goodsIntegral;
    }

    public void setGoodsIntegral(Integer goodsIntegral) {
        this.goodsIntegral = goodsIntegral;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderPerson() {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson) {
        this.orderPerson = orderPerson;
    }

    public Long getAddressId() {
        return AddressId;
    }

    public void setAddressId(Long addressId) {
        AddressId = addressId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public String getExpressName() {
        return ExpressName;
    }

    public void setExpressName(String expressName) {
        ExpressName = expressName;
    }

    public Integer getExpressNum() {
        return ExpressNum;
    }

    public void setExpressNum(Integer expressNum) {
        ExpressNum = expressNum;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getProvincialCity() {
        return ProvincialCity;
    }

    public void setProvincialCity(String provincialCity) {
        ProvincialCity = provincialCity;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExNum() {
        return exNum;
    }

    public void setExNum(String exNum) {
        this.exNum = exNum;
    }
}
