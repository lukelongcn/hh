package com.h9.store.modle.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/1/22.
 */
@Getter
@Setter
@Accessors(chain = true)
public class PayResultVO {


    private PayResult payResult;

    private Object wxPayInfo;

    private Object alipayInfo;

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PayResult{
        /**
         * description: 支付宝为2，微信为1
         */
        private String payMethod = "";
        private boolean resumePay = false;
    }




    public PayResultVO(PayResult payResult) {
        this.payResult = payResult;
    }

    public PayResultVO(PayResult payResult, Object wxPayInfo) {
        this.payResult = payResult;
        this.wxPayInfo = wxPayInfo;
    }

    public PayResultVO( ) {
    }
}
