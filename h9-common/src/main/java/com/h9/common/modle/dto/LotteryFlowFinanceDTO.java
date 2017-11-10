package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/8 11:26
 */
public class LotteryFlowFinanceDTO extends PageDTO{
    @Size(max = 64,message = "兑奖码过长")
    @ApiModelProperty(value = "兑奖码")
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
