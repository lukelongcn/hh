package com.h9.api.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by itservice on 2017/10/31.
 */
public class MobileRechargeDTO {
    @Min(value = 20,message = "请传入合法充值金额")
    private Integer cardNum;
    @Size(min = 11,max = 11,message = "请输入合法的手机号码")
    private Long tel;

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }
}
