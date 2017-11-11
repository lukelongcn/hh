package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
public class UserRecordVO {
    @ApiModelProperty(value = "用户id ")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String nickName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "openId")
    private String openId;
    @ApiModelProperty(value = "开瓶次数")
    private Long openCount;
    @ApiModelProperty(value = "参与次数")
    private Long joinCount;

    public UserRecordVO(Long userId, String nickName, String phone, String openId, Long openCount, Long joinCount) {
        this.userId = userId;
        this.nickName = nickName;
        this.phone = phone;
        this.openId = openId;
        this.openCount = openCount;
        this.joinCount = joinCount;
    }

    public UserRecordVO() {
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

    public Long getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Long openCount) {
        this.openCount = openCount;
    }

    public Long getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Long joinCount) {
        this.joinCount = joinCount;
    }
}