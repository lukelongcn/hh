package com.h9.admin.model.dto.basis;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: George
 * @date: 2017/11/7 14:55
 */
public class BankTypeEditDTO extends BankTypeAddDTO{
    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
