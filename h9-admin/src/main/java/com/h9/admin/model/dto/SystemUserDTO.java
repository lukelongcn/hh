package com.h9.admin.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: George
 * @date: 2017/10/30 19:19
 */
public class SystemUserDTO {

    @NotEmpty(message = "用户名不能为空")
    @Size(max = 255,message = "用户名过长")
    //@NotNull(message = "用户名不能为空")
    //@Max(value = 255,message = "用户名过长")
    private String name;

    @NotEmpty( message = "密码不能为空")
    @Size(max = 255,message = "用户名过长")
    //@NotNull(message = "密码不能为空")
    //@Max(value = 255,message = "密码过长")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
