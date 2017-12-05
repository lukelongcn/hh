package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by 李圆
 * on 2017/11/2
 */
public class BankCardDTO {

    @Size(min = 6, message = "请填写正确的银行卡号")
    @NotBlank(message = "请填写银行卡号")
    private String no;
    @NotBlank(message = "请填写真实姓名")
    @Size(max = 32,message = "姓名过长")
    private String name;
    @NotNull(message = "请填写银行类型 id")
    private Long bankTypeId;//银行类别
    @NotBlank(message = "请填写provice")
    private String provice;
    @NotBlank(message = "请填写city")
    private String city;
    @NotBlank(message = "请传入短信验证码")
    private String smsCode;


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
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

    public Long getBankName() {
        return bankTypeId;
    }

    public Long getBankTypeId() {
        return bankTypeId;
    }

    public void setBankTypeId(Long bankTypeId) {
        this.bankTypeId = bankTypeId;
    }

    public void setBankName(Long bankName) {
        this.bankTypeId = bankName;
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


}
