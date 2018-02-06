package com.h9.admin.model.dto.community;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/29 16:13
 */
public class GoodsTypeEditDTO extends GoodsTypeAddDTO{
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
