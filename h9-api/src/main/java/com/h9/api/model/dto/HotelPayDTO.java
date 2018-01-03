package com.h9.api.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2018/1/3.
 */
@Data
@Accessors(chain = true)
public class HotelPayDTO {
    @NotNull(message = "请选择支付方式")
    private Integer payMethod;

    @NotNull(message = "请选择要支付的订单Id")
    private Long hotelOrderId;
}
