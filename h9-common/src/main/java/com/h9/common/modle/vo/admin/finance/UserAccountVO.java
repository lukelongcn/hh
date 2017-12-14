package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserAccount;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
public class UserAccountVO {
    @ApiModelProperty(value = "用户id ")
    private Long userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value ="呢你")
    private String nickName;

    @ApiModelProperty(value ="余额")
    private BigDecimal balance = new BigDecimal(0);

    @ApiModelProperty(value ="v币")
    private BigDecimal vCoins = new BigDecimal(0);
    @ApiModelProperty(value ="注册时间")
    private Date createTime;

    public static UserAccountVO toUserAccountVO( UserAccount userAccount){
        UserAccountVO userAccountVO = new UserAccountVO();
        //BeanUtils.copyProperties(user,userAccountVO);
        BeanUtils.copyProperties(userAccount,userAccountVO);
        return userAccountVO;
    }

    public UserAccountVO(User user, UserAccount userAccount){
        BeanUtils.copyProperties(userAccount,this);
        BeanUtils.copyProperties(user,this);
    }

    public UserAccountVO() {
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getvCoins() {
        return vCoins;
    }

    public void setvCoins(BigDecimal vCoins) {
        this.vCoins = vCoins;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
