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

    @ApiModelProperty(value = "订单状态,0:未确认,1:等待发货,2:等待收货,3:已取消 ,4:交易成功,5:交易失败,6:退货受理中,7:退货中,8:不予退货,9:退货完成")
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
