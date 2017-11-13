package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
public class BlackAccountVO {
    @ApiModelProperty(value = "id ")
    private Long id;
    @ApiModelProperty(value = "手机串号 ")
    private String imei;
    @ApiModelProperty(value = "关联账号数")
    private Long relevanceCount;
    @ApiModelProperty(value = "用户id ")
    private Long userId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "openId")
    private String openId;
    @ApiModelProperty(value = "加入时间")
    private Date createTime;
    @ApiModelProperty(value = "解禁时间")
    private Date endTime;
    @ApiModelProperty(value = "原因")
    private String cause;

    

    public BlackAccountVO() {
    }

    public BlackAccountVO(Long id,Long userId, String nickName, String phone, String openId, Date createTime, Date endTime, String cause) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.phone = phone;
        this.openId = openId;
        this.createTime = createTime;
        this.endTime = endTime;
        this.cause = cause;
    }

    public BlackAccountVO(Long id, String imei, Date createTime, Date endTime, String cause) {
        this.id = id;
        this.imei = imei;
        this.createTime = createTime;
        this.endTime = endTime;
        this.cause = cause;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getRelevanceCount() {
        return relevanceCount;
    }

    public void setRelevanceCount(Long relevanceCount) {
        this.relevanceCount = relevanceCount;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}