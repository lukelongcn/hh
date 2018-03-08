package com.h9.admin.model.dto;

import com.h9.common.db.entity.PayInfo;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Created by Ln on 2018/3/6.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class WxOrderListInfo {
    @ApiModelProperty(value = "微信订单号")
    private String wxOrderNo;
    @ApiModelProperty(value = "内部订单类型")
    private String orderType;
    @ApiModelProperty(value = "内部系统订单id")
    private String orderId;
    @ApiModelProperty(value = "金额")
    private String money;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "支付时间")
    private String payDate;
    @ApiModelProperty(value = "订单详情")
    private String detailUrl;

    public WxOrderListInfo(PayInfo payInfo, Map<String, String> map) {
        Long id = payInfo.getId();
        int type = payInfo.getOrderType();
        this.orderType = type == 0 ? "微信充值" : "购买商品";
        this.money = MoneyUtils.formatMoney(payInfo.getMoney());
        this.createTime = DateUtil.formatDate(payInfo.getCreateTime(), DateUtil.FormatType.SECOND);
        int orderType = payInfo.getOrderType();
        if(orderType == PayInfo.OrderTypeEnum.Recharge.getId()){

        }
        this.orderId = payInfo.getOrderId()+"";
        this.wxOrderNo = map.get(id+"");
        this.payDate = DateUtil.formatDate(payInfo.getCreateTime(), DateUtil.FormatType.SECOND);
    }
}
