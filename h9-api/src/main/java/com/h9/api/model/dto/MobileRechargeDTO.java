package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by itservice on 2017/10/31.
 */
public class MobileRechargeDTO {
    @Min(value = 0,message = "请填写Id")
    private Long id;
    @Size(min = 11,max = 11,message = "请输入合法的手机号码")
    private Long tel;
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

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }
}
