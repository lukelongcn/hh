package com.h9.admin.model.dto;

import com.h9.common.db.entity.PayInfo;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Ln on 2018/3/6.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxOrderListInfo {
    private String wxOrderNo;

    private String orderType;

    private String orderId;

    private String money;

    private String createTime;

    private String payDate;

    public WxOrderListInfo(PayInfo payInfo, String tid) {
        Long id = payInfo.getId();
        int type = payInfo.getOrderType();
        this.orderType = type == 0 ? "微信充值" : "购买商品";
        this.money = MoneyUtils.formatMoney(payInfo.getMoney());
        this.createTime = DateUtil.formatDate(payInfo.getCreateTime(), DateUtil.FormatType.SECOND);
        this.wxOrderNo = tid;

    }
}
