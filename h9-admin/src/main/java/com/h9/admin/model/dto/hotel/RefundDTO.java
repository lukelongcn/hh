package com.h9.admin.model.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by ldh on 2017/8/10.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RefundDTO {

    private BigDecimal total_fee;

    private String mchId;

    private String appId;

    private String payKey;

    private String orderId;

    private String businessAppId;

    private byte[] certByte;

    public RefundDTO() {
    }
}
