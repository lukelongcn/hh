package com.h9.admin.model.dto.basis;

import com.h9.common.db.entity.User;
import com.h9.common.utils.MD5Util;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/11 14:58
 */
public class SystemUserAddDTO {

    @ApiModelProperty(value = "姓名",required = true)
    @NotBlank(message = "姓名不能为空")
    private String nickName;

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "状态",required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User toUser(User user){
        if(user==null){
            user = new User();
        }
        BeanUtils.copyProperties(this,user);
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        user.setIsAdmin(User.IsAdminEnum.ADMIN.getId());
        user.setLoginCount(0);
        return  user;
    }
}
