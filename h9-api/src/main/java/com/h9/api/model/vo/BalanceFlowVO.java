package com.h9.api.model.vo;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.VCoinsFlow;
import com.h9.common.utils.DateUtil;
import org.springframework.beans.BeanUtils;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * BalanceFlowVO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 16:58
 */
public class BalanceFlowVO {


    private BigDecimal money = new BigDecimal(0);
    private String month;
    private String createTime;
    private String remarks;

    public BalanceFlowVO(BalanceFlow balanceFlow) {
        BeanUtils.copyProperties(balanceFlow,this);
        Date createTime = balanceFlow.getCreateTime();
        month = DateUtil.formatDate(createTime, DateUtil.FormatType.GBK_MONTH);
        remarks = DateUtil.formatDate(createTime, DateUtil.FormatType.SECOND);
    }

    public BalanceFlowVO(VCoinsFlow vCoinsFlow) {
        BeanUtils.copyProperties(vCoinsFlow,this);
        Date createTime = vCoinsFlow.getCreateTime();
        month = DateUtil.formatDate(createTime, DateUtil.FormatType.GBK_MONTH);
        remarks = DateUtil.formatDate(createTime, DateUtil.FormatType.SECOND);
    }


    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
