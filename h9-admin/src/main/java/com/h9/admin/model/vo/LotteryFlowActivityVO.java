package com.h9.admin.model.vo;

import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.UserRecord;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/8 13:52
 */
public class LotteryFlowActivityVO {
    @ApiModelProperty(value = "id" )
    private Long id;

    @ApiModelProperty(value = "用户id" )
    private Long userId;

    @ApiModelProperty(value = "兑奖码" )
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "金额" )
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value = "经度" )
    private double longitude;

    @ApiModelProperty(value = "纬度" )
    private double latitude;

    @ApiModelProperty(value = "ip地址" )
    private String ip;

    @ApiModelProperty(value = "手机品牌" )
    private String phoneType;

    @ApiModelProperty(value = "版本" )
    private String version;

    @ApiModelProperty(value = "创建时间" )
    private Date createTime;

    @ApiModelProperty(value = "地址" )
    private String address;

    @ApiModelProperty(value = "客户端")
    private String client;

    @ApiModelProperty(value = "imei")
    private String imei;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = UserRecord.ClientEnum.getValue(client);
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public LotteryFlowActivityVO(Lottery lottery){
        BeanUtils.copyProperties(lottery.getReward(), this);
        BeanUtils.copyProperties(lottery.getUser(), this);
        BeanUtils.copyProperties(lottery.getUserRecord(), this);
        BeanUtils.copyProperties(lottery, this);
    }
}
