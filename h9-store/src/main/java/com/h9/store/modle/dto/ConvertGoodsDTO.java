package com.h9.store.modle.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by 李圆 on 2017/11/29.
 */
@Data
@Accessors(chain = true)
public class ConvertGoodsDTO {

    @NotNull(message = "请选择地址")
    private Long addressId;

    @NotNull(message = "请选择您要兑换的数量")
    private Integer count;

    @NotNull(message = "请选择您要兑换的商品")
    private Long goodsId;

    //WX(2, "wx"), WXJS(3, "wxjs")
    @NotNull(message = "请传入支付平台类型，如，'wx'(微信APP）'wxjs'(公众号)")
    private String payPlatform;


    /**
     * description:
     *   @see com.h9.common.db.entity.order.Orders.PayMethodEnum
     */
    @NotNull(message = "请选择支付方式")
    private Integer payMethod;

    /** 用户优惠券id */
    private Long userCouponsId;
}
