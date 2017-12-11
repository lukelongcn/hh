package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2017/11/2.
 */
public class DidiCardVerifyDTO {
    @NotNull(message = "id为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
