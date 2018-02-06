package com.h9.api.model.vo;

import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.utils.MoneyUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/3.
 */
@Data
@Accessors(chain = true)
public class HotelOrderPayVO {

    private String tips;

    private String orderMoney;

    private String balance;

    private Long hotelOrderId;

    private String paidMoney;

    private String unpaidMoney;

    private String hotelName;

    public HotelOrderPayVO(){}

    public HotelOrderPayVO(HotelOrder hotelOrder, UserAccount userAccount, BigDecimal totalMoney){
        this.setBalance(MoneyUtils.formatMoney(userAccount.getBalance()))
                .setOrderMoney(MoneyUtils.formatMoney(totalMoney))
                .setTips("请在23:59之前完成支付，过期将自动取消订单")
                .setHotelOrderId(hotelOrder.getId());
        BigDecimal payMoney4JiuYuan = hotelOrder.getPayMoney4JiuYuan();
        BigDecimal payMoney4Wechat = hotelOrder.getPayMoney4Wechat();

        BigDecimal paidMoneyDe = new BigDecimal(0);
        if(payMoney4JiuYuan != null){
            this.setPaidMoney(MoneyUtils.formatMoney(payMoney4JiuYuan));
            paidMoneyDe= paidMoneyDe.add(payMoney4JiuYuan);
        }

        if(payMoney4Wechat != null){
            this.setPaidMoney(MoneyUtils.formatMoney(payMoney4Wechat));
            paidMoneyDe = paidMoneyDe.add(payMoney4Wechat);
        }

        this.paidMoney = MoneyUtils.formatMoney(paidMoneyDe);
        BigDecimal unpayMoney = totalMoney.subtract(paidMoneyDe);
        this.setUnpaidMoney(MoneyUtils.formatMoney(unpayMoney));
        this.hotelName = hotelOrder.getHotelName();

    }
}
