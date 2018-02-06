package com.h9.common.modle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by itservice on 2018/2/6.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StorePayDTO {

    private Long orderId;

    private BigDecimal payMoney;

    private Long userId;

    private String payPlatform;


}
