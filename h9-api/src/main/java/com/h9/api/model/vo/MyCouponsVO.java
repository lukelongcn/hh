package com.h9.api.model.vo;

import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.OrderItems;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
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
    private String imgUrl;


    public MyCouponsVO(OrderItems items) {
        this.price = MoneyUtils.formatMoney(items.getPrice());
        this.name = items.getName();
        this.status = "已发放";
        this.createTime = DateUtil.formatDate(items.getCreateTime(), DateUtil.FormatType.MINUTE);
        this.from = "大转盘奖励";
        this.cardNumber = items.getDidiCardNumber();
        this.imgUrl = "https://cdn-h9-img.thy360.com/FuQuxK79VYsa9cut_Uy6mxIXkE9e";
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
