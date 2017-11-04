package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by 李圆
 * on 2017/11/2
 */
public class BankCardDTO {

    private Long user_id;

    @Size(min = 19,max = 19,message = "请填写正确的银行卡号")
    @NotEmpty(message = "请填写银行卡号")
    private String no;

    @NotEmpty(message = "请填写真实姓名")
    private String name;

    private String bankTypes;//银行类别

    private String provice;

    private String city;

    private Integer status = 1;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankTypes() {
        return bankTypes;
    }

    public void setBankTypes(String bankTypes) {
        this.bankTypes = bankTypes;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
