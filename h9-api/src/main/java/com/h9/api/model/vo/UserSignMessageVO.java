package com.h9.api.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李圆 on 2018/1/2
 */
public class UserSignMessageVO {
    private BigDecimal cashBack = new BigDecimal(0);

    private Integer signCount = 0;

    private Integer signDays = 0;

    private List<SignVO> list;

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

    public List<SignVO> getList() {
        return list;
    }

    public void setList(List<SignVO> list) {
        this.list = list;
    }
}
