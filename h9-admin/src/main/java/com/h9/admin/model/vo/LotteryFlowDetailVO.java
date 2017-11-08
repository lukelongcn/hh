package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/8 16:04
 */
public class LotteryFlowDetailVO {

    @ApiModelProperty(value = "用户id" )
    private Long userId;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "金额" )
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value = "经度" )
    private double longitude;

    @ApiModelProperty(value = "纬度" )
    private double latitude;

    @ApiModelProperty(value = "手机号码" )
    private String phoneType;

    @ApiModelProperty(value = "版本" )
    private String version;

    @ApiModelProperty(value = "时间" )
    protected Date createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
