package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by itservice on 2017/10/31.
 */
public class MobileRechargeVerifyDTO {

    private Long id;

    @Size(min = 11,max = 11,message = "请填写正确的手机号")
    @NotBlank(message = "请输入合法的手机号码")
    private String tel;

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
