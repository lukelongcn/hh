package com.h9.admin.model.dto.order;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/12/22 16:01
 */
public class OrderStatusDTO {
    @ApiModelProperty(value = "订单状态",required = true)
    @NotNull(message = "订单状态不能为空")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
