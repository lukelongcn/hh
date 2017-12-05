package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2017/11/27
 */
public class AddressDTO {
    @NotNull(message = "请填写所在省")
    private Long pid;
    @NotNull(message = "请填写所在市")
    private Long cid;
    @NotNull(message = "请填写所在区")
    private Long aid;

    private Long userId;

    //收货人姓名
    @NotBlank(message = "请填写真实姓名")
    @Size(max = 20,message = "姓名过长")
    private String name;

    // 收货人手机号码
    @Size(min = 7,max = 16,message = "请填写正确的手机号")
    @NotBlank(message = "请输入合法的手机号码")
    private String phone;

    /**  默认地址*/
    private Integer defaultAddress;



    // 详细地址
    @NotBlank(message = "请填写详细地址")
    @Size(max = 100,message = "详细地址过长")
    private String address;

    /** 省市区编号*/
    private String provincialCity;

    /** 使用状态 */
    private Integer status;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

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
    
    public String getProvincialCity() {
        return provincialCity;
    }

    public void setProvincialCity(String provincialCity) {
        this.provincialCity = provincialCity;
    }
}
