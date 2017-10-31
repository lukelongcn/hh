package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: George
 * @date: 2017/10/31 16:39
 */
@ApiModel(value="LoginResultVO",description = "登录返回结果")
public class LoginResultVO {
    @ApiModelProperty(value = "用户token" )
    private String token;
    @ApiModelProperty(value = "用户名" )
    private String name;

    public LoginResultVO(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
