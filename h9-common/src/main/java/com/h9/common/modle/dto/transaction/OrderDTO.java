package com.h9.common.modle.dto.transaction;

import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/12/5 14:10
 */
public class OrderDTO extends PageDTO{
    @ApiModelProperty(value = "订单编号")
    private String no;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "下单开始时间")
    private Date startTime;

    @ApiModelProperty(value = "下单结束时间")
    private Date endTime;

    @ApiModelProperty(value = "订单状态,1:待发货,2:已发货,3:已完成")
    @NotNull(message = "状态不能为空")
    private Integer status;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public void setStartTime(long startTime) {
        this.startTime = new Date(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = new Date(endTime);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
