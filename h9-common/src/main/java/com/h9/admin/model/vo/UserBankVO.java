package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
public class UserBankVO {
    @ApiModelProperty(value = "id ")
    private Long id;
    @ApiModelProperty(value = "用户id ")
    private Long userId;
    @ApiModelProperty(value = "持卡人姓名")
    private String name;
    @ApiModelProperty(value = "银行卡号")
    private String no;
    @ApiModelProperty(value = "开户省")
    private String province;
    @ApiModelProperty(value = "开户城市")
    private String city;
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalsMoney;
    @ApiModelProperty(value = "提现次数")
    private Long withdrawalsCount;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "状态 1:正常 2禁用 3解绑")
    private int status;
    public UserBankVO() {
    }

    public UserBankVO(Long id, Long userId, String name, String no, String province, String city, BigDecimal withdrawalsMoney, Long withdrawalsCount, Date createTime, Date updateTime,int status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.no = no;
        this.province = province;
        this.city = city;
        this.withdrawalsMoney = withdrawalsMoney;
        this.withdrawalsCount = withdrawalsCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWithdrawalsMoney() {
        if(withdrawalsMoney==null){
            return BigDecimal.valueOf(0).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();
        }
        return withdrawalsMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public void setWithdrawalsMoney(BigDecimal withdrawalsMoney) {
        this.withdrawalsMoney = withdrawalsMoney;
    }

    public Long getWithdrawalsCount() {
        return withdrawalsCount;
    }

    public void setWithdrawalsCount(Long withdrawalsCount) {
        this.withdrawalsCount = withdrawalsCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}