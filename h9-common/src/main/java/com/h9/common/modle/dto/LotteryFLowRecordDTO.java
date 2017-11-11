package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.Set;

/**
 * @author: George
 * @date: 2017/11/10 20:03
 */
public class LotteryFLowRecordDTO extends PageDTO{
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "兑奖码")
    private String code;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "转账开始时间")
    private Date startTime;

    @ApiModelProperty(value = "转账结束时间")
    private Date endTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = new Date(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = new Date(endTime);
    }
}
