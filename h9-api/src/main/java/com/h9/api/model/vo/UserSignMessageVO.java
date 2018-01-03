package com.h9.api.model.vo;

import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李圆 on 2018/1/2
 */
public class UserSignMessageVO {
    // 签到奖励金额
    private BigDecimal cashBack = new BigDecimal(0);

    // 总共签到天数
    private Integer signCount = 0;

    // 连续签到天数
    private Integer signDays = 0;

    // 用户余额
    private BigDecimal balance = new BigDecimal(0);

    // 头像
    private String avatar ;

    // 最新签到列表
    private List list;

    // 是否签到
    private Integer isSign;

    public UserSignMessageVO(BigDecimal balance ,User user ,List list,Integer isSign){
        this.cashBack = new BigDecimal(0);
        this.signCount = 0;
        this.signDays = 0;
        this.avatar = user.getAvatar();
        this.balance = balance;
        this.list = list;
        this.isSign = isSign;
    }

    public UserSignMessageVO(BigDecimal balance ,User user ,UserSign userSign,List list,Integer isSign){
        this.cashBack = userSign.getCashBack();
        this.signCount = user.getSignCount();
        this.signDays = user.getSignDays();
        this.avatar = user.getAvatar();
        this.balance = balance;
        this.list = list;
        this.isSign = isSign;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getCashBack() {
        return cashBack;
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getSignDays() {
        return signDays;
    }

    public void setSignDays(Integer signDays) {
        this.signDays = signDays;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
