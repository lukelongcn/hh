package com.h9.lottery.model.dto;

import com.h9.common.db.entity.lottery.LotteryFlow;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * BalanceFlowVO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 16:58徽
 */
public class LotteryFlowDTO {

    private String money = "0.00";
    private String month;
    private String createTime;
    private String remarks;

    public LotteryFlowDTO() {

    }


    public LotteryFlowDTO(LotteryFlow lotteryFlow) {
        Date createDate = lotteryFlow.getCreateTime();
        createTime = DateUtil.formatDate(createDate, DateUtil.FormatType.SECOND);
        month = DateUtil.formatDate(createDate, DateUtil.FormatType.GBK_MONTH);
        money = MoneyUtils.formatMoney(lotteryFlow.getMoney());
        remarks = lotteryFlow.getRemarks();
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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
