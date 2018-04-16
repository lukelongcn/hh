package com.h9.store.modle.vo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * OrderVo:itservice
 * Date: 2018/1/8
 * Time: 15:40
 */
@Data
public class OrderVo {
    private Long payOrderId;
    private String openId;
    private String orderNo;
    private String businessOrderId;
    private BigDecimal totalAmount;
    private String businessAppId;


    public OrderVo() {
    }

}