package com.h9.api.model.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2017/10/27.
 */
public class UserLoginDTO {
    @NotNull(message = "请填写手机号码")
    private String phone;
    @NotNull(message = "请填写验证码")
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
