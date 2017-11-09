package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by itservice on 2017/10/27.
 */
public class UserLoginDTO {
    @Size(min = 11,max = 11,message = "请填写正确的手机号")
    @NotBlank(message = "请填写手机号码")
    private String phone;

    @NotBlank(message = "请填写验证码")
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
