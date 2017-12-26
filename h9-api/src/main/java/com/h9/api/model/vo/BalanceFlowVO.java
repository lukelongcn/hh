package com.h9.api.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.VCoinsFlow;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * BalanceFlowVO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 16:58
 */
public class BalanceFlowVO {


    private String money = "0.00";
    private String month;
    private String remarks;
    private String imgUrl;
    private String createTime;

    public BalanceFlowVO(BalanceFlow balanceFlow, Map<String, String> iconMap, Map<String, String> nameMap) {
        BeanUtils.copyProperties(balanceFlow, this);
        Date createTime = balanceFlow.getCreateTime();
        month = DateUtil.formatDate(createTime, DateUtil.FormatType.GBK_MONTH);
        remarks = balanceFlow.getRemarks();
        money = MoneyUtils.formatMoney(balanceFlow.getMoney());
        this.createTime = DateUtil.formatDate(balanceFlow.getCreateTime(), DateUtil.FormatType.SECOND);
        imgUrl = iconMap.get(balanceFlow.getFlowType() + "");
//        remarks = nameMap.get(balanceFlow.getFlowType() + "");
    }

    public BalanceFlowVO(VCoinsFlow vCoinsFlow, Map<String, String> iconMap, Map<String, String> nameMap) {

        BeanUtils.copyProperties(vCoinsFlow, this);
        Date createTime = vCoinsFlow.getCreateTime();
        month = DateUtil.formatDate(createTime, DateUtil.FormatType.GBK_MONTH);
        remarks = vCoinsFlow.getRemarks();
        imgUrl = iconMap.get(vCoinsFlow.getvCoinsflowType() + "");
        this.money = vCoinsFlow.getMoney().toString();
        this.createTime = DateUtil.formatDate(createTime, DateUtil.FormatType.SECOND);
        remarks = nameMap.get(vCoinsFlow.getvCoinsflowType() + "");
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

}
