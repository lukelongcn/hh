package com.h9.api.model.vo;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.VCoinsFlow;
import com.h9.common.utils.DateUtil;
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


    private BigDecimal money = new BigDecimal(0);
    private String month;
    private String remarks;
    private String imgUrl;
    private String createTime;

    public BalanceFlowVO(BalanceFlow balanceFlow,Map<String, String> iconMap) {
        BeanUtils.copyProperties(balanceFlow,this);
        Date createTime = balanceFlow.getCreateTime();
        month = DateUtil.formatDate(createTime, DateUtil.FormatType.GBK_MONTH);
        remarks = balanceFlow.getRemarks();
        money = balanceFlow.getMoney();
        this.createTime = DateUtil.formatDate(balanceFlow.getCreateTime(), DateUtil.FormatType.SECOND);
        Set<String> keySet = iconMap.keySet();

        for(String key : keySet){
            if(key.equals(balanceFlow.getFlowType()+"")){
                imgUrl=iconMap.get(key);
                break;
            }
        }
    }

    public BalanceFlowVO(VCoinsFlow vCoinsFlow) {
        BeanUtils.copyProperties(vCoinsFlow,this);
        Date createTime = vCoinsFlow.getCreateTime();
        month = DateUtil.formatDate(createTime, DateUtil.FormatType.GBK_MONTH);
        remarks = DateUtil.formatDate(createTime, DateUtil.FormatType.SECOND);
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

}
