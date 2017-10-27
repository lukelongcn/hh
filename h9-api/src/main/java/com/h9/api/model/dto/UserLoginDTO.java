package com.h9.api.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by itservice on 2017/10/27.
 */
public class UserLoginDTO {
    @Size(min = 11,max = 11,message = "请填写正确的手机号")
    @NotNull(message = "请填写手机号码")
    private String phone;
    @Size(min = 4,max = 4,message = "请填写正确格式的验证码")
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
