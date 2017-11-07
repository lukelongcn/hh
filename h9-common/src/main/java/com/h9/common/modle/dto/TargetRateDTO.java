package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/5 18:32
 */
public class TargetRateDTO {

    @ApiModelProperty(value = "位置",required = true)
    @NotNull(message = "位置不能为空")
    private Integer pos;
    @ApiModelProperty(value = "比例",required = true)
    @NotNull(message = "比例不能为空")
    private  Float rate;

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
