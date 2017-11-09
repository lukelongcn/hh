package com.h9.admin.model.dto.finance;


import io.swagger.annotations.ApiModelProperty;


/**
 * @author: George
 * @date: 2017/11/9 14:06
 */
public class WithdrawRecordQueryDTO {
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
