package com.h9.admin.model.dto.basis;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/11 14:58
 */
public class SystemUserEditDTO extends SystemUserAddDTO{
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
