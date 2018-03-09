package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Ln on 2018/3/9.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class FundsInfo {
    @ApiModelProperty("提现总金额")
    private String withdrawMoneySum;

    @ApiModelProperty("充值总金额")
    private String rechargeMoneySum;

    @ApiModelProperty("订单总金额")
    private String orderMoneySum;

    @ApiModelProperty("微信支付总金额")
    private String payMoney4wxSum;
    @ApiModelProperty("余额支付金额")
    private String payMoney4balanceSum;
    @ApiModelProperty("余额总和")
    private String balanceSum;
}
