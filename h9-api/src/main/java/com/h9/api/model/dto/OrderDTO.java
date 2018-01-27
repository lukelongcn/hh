package com.h9.api.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * OrderDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/8
 * Time: 15:03
 */
@Data
public class OrderDTO {

    private String openId ="";
    private String orderNo = "";
    private Long businessOrderId ;
    private BigDecimal totalAmount = new BigDecimal(0);
    private String businessAppId = "" ;
    private int payMethod = PayMethodEnum.WXJS.getKey();


    public enum PayMethodEnum {
        BALANCE(0, "balance"), ALIPAY(1, "alipay"), WX(2, "wx"), WXJS(3, "wxjs"), YWT(4, "ywt"),
        WXSCAN(5, "wxscan"), ALISCAN(6, "aliscan"), WXMINI(7, "wxmini"), ALINATIVE(8, "alinative");

        private int key;
        private String value;

        public static PayMethodEnum valueOf(int key) {
            switch (key) {
                case 0:
                    return BALANCE;
                case 1:
                    return ALIPAY;
                case 2:
                    return WX;
                case 3:
                    return WXJS;
                case 4:
                    return YWT;
                case 5:
                    return WXSCAN;
                case 6:
                    return ALISCAN;
                case 7:
                    return WXMINI;
                case 8:
                    return ALINATIVE;
                default:
                    return null;
            }
        }
        public static PayMethodEnum getByValue(String value) {
            PayMethodEnum[] season = values();
            for (PayMethodEnum s : season) {
                if (s.getValue().equals(value)) {
                    return s;
                }
            }
            return null;
        }


        private PayMethodEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }



    }



}
