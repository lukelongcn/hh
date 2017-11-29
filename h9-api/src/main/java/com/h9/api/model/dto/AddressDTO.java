package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2017/11/27
 */
public class AddressDTO {

    private Long userId;

    //收货人姓名
    @NotBlank(message = "请填写真实姓名")
    @Size(max = 32,message = "姓名过长")
    private String name;

    // 收货人手机号码
    @Size(min = 11,max = 11,message = "请填写正确的手机号")
    @NotBlank(message = "请输入合法的手机号码")
    private String phone;

    // 默认地址
    private Integer defaultAddress;

    // 所在省
    @NotBlank(message = "请填写所在省")
    private String province;

    // 所在市
    @NotBlank(message = "请填写所在市")
    private String city;

    // 所在区
    /**@NotBlank(message = "请填写所在区")*/
    private String distict;

    //详细地址
    @NotBlank(message = "请填写详细地址")
    private String address;

    //省市区编号
    private String provincialCity;

    //使用状态
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getProvincialCity() {
        return provincialCity;
    }

    public void setProvincialCity(String provincialCity) {
        this.provincialCity = provincialCity;
    }
}
