package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/11/2.
 */
public class DidiCardDTO {
    @NotNull(message = "请填写价格参数")
    private BigDecimal price;

    @NotEmpty(message = "请填写验证码")
    private String code;
    public BigDecimal getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
