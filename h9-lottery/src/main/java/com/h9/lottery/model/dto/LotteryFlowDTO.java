package com.h9.lottery.model.dto;

import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.VCoinsFlow;
import com.h9.common.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * BalanceFlowVO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 16:58徽
 */
public class LotteryFlowDTO {

    private BigDecimal money = new BigDecimal(0);
    private String month;
    private String createTime;
    private String remarks;


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
