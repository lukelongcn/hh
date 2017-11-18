package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2017/11/2.
 */
public class DidiCardDTO {
    @NotNull(message = "id为空")
    private Long id;

    @NotBlank(message = "请填写验证码")
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
}
