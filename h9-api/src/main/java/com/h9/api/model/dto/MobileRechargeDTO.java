package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2017/10/31.
 */
public class MobileRechargeDTO {
//    @Size(max = 20,message = "请填写Id")
//    @NotEmpty(message = "请填写id")
    private Long id;
    @NotEmpty(message = "请输入合法的手机号码")
    private String tel;
    @NotEmpty(message = "请传code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
