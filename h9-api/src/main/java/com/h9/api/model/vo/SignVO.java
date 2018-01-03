package com.h9.api.model.vo;

import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 李圆 on 2018/1/2
 */
public class SignVO {
    private BigDecimal cashBack = new BigDecimal(0);

    private Integer signCount = 0;

    private String nickName;

    private Integer signDays = 0;

    protected String createTime ;


    public SignVO(User user,UserSign userSign){
        BeanUtils.copyProperties(user,this);
        this.cashBack = userSign.getCashBack();
    }

    public SignVO(UserSign userSign) {
        this.cashBack = userSign.getCashBack();
        this.createTime = DateUtil.formatDate(userSign.getCreateTime(), DateUtil.FormatType.SECOND);
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = DateUtil.formatDate(createTime, DateUtil.FormatType.SECOND);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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


    public static SignVO convert(UserSign userSign) {
        return new SignVO(userSign);
    }
}
