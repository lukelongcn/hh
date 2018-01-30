package com.h9.api.model.vo;

import com.ctc.wstx.util.DataUtil;
import com.h9.common.db.entity.user.UserSign;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import javax.xml.crypto.Data;

/**
 * Created by 李圆 on 2018/1/3
 */
public class SelfSignVO {
    protected String createTime ;
    private String hour;

    private BigDecimal cashBack = new BigDecimal(0);

    public SelfSignVO(UserSign userSign){
        BeanUtils.copyProperties(userSign,this);
        this.createTime = DateUtil.formatDate(userSign.getCreateTime(), DateUtil.FormatType.DAY);
        this.hour = DateUtil.formatDate(userSign.getCreateTime(), DateUtil.FormatType.SINGLE_HOUR);
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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
