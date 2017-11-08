package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: George
 * @date: 2017/11/8 11:26
 */
public class LotteryFlowDTO extends PageDTO{
    @ApiModelProperty(value = "兑奖码")
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "中奖状态")
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
