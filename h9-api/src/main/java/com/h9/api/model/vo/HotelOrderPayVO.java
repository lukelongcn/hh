package com.h9.api.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

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
}
