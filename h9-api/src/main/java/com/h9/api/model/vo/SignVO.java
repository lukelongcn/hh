package com.h9.api.model.vo;

import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;

import java.math.BigDecimal;

/**
 * Created by 李圆 on 2018/1/2
 */
public class SignVO {
    private BigDecimal cashBack = new BigDecimal(0);

    private Integer signCount = 0;

    private Integer signDays = 0;

    public SignVO(User user,UserSign userSign){
        this.cashBack = userSign.getCashBack();
        this.signCount = user.getSignCount();
        this.signDays = user.getSignDays();
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
}
