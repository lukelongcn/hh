package com.h9.api.model.vo;

import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.OrderItems;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Created by itservice on 2017/11/2.
 */
public class MyCouponsVO {
    private String price;
    private String name;
    private String status;
    private String createTime;
    private String from;
    private String cardNumber;


    public MyCouponsVO(OrderItems items, Goods goods) {
        this.price = items.getPrice().toString();
        this.name = items.getName();
        this.status = "已发放";
        this.createTime = DateUtil.formatDate(items.getCreateTime(), DateUtil.FormatType.MINUTE);
        this.from = "大转盘奖励";
        //TODO 重构完放开
//        if(goods != null)
//        this.cardNumber = goods.getDiDiCardNumber() == null ? "":goods.getDiDiCardNumber();
        //TODO 重构完放开
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
