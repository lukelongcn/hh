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
    @NotEmpty(message = "请填写手机号码")
    private String tel;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
