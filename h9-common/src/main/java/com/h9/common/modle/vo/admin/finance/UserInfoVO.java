package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/30 15:52
 */
public class UserInfoVO {
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "用户头像")
    private String avatar ;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后登陆时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "是否为管理员")
    private String isAdmin;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = User.StatusEnum.getNameById(status);
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = User.IsAdminEnum.getNameById(isAdmin);
    }

    public UserInfoVO() {
    }

    public UserInfoVO(User user) {
        BeanUtils.copyProperties(user,this);
    }
}
