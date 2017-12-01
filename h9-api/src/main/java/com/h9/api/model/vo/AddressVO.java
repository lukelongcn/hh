package com.h9.api.model.vo;


import com.h9.common.db.entity.Address;

import org.springframework.beans.BeanUtils;

/**
 * Created by 李圆 on 2017/11/28
 */
public class AddressVO {

    // 地址id
    private Long id;

    private Long userId;

    //收货人姓名
    private String name;

    // 收货人手机号码
    private String phone;

    // 默认地址
    private Integer defaultAddress;

    // 所在省
    private String province;

    // 所在市
    private String city;

    // 所在区
    private String distict;

    //详细地址
    private String address;

    //省市区编号
    private String provincialCity;

    //使用状态
    private Integer status;

    public AddressVO(Address address) {
        BeanUtils.copyProperties(address,this);
    }

    public static AddressVO  convert(Address address){
        return new AddressVO(address);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistict() {
        return distict;
    }

    public void setDistict(String distict) {
        this.distict = distict;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvincialCity() {
        return provincialCity;
    }

    public void setProvincialCity(String provincialCity) {
        this.provincialCity = provincialCity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
