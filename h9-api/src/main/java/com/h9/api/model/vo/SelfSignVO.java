package com.h9.api.model.vo;

import com.h9.common.db.entity.user.UserSign;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * Created by 李圆 on 2018/1/3
 */
public class SelfSignVO {
    protected String createTime ;

    private BigDecimal cashBack = new BigDecimal(0);

    public SelfSignVO(UserSign userSign){
        BeanUtils.copyProperties(userSign,this);
        this.createTime = DateUtil.formatDate(userSign.getCreateTime(), DateUtil.FormatType.SECOND);
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getCashBack() {
        return cashBack;
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }
}
