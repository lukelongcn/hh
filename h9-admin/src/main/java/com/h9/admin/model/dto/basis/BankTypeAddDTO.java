package com.h9.admin.model.dto.basis;

import com.h9.common.db.entity.account.BankType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author: George
 * @date: 2017/11/7 14:38
 */
public class BankTypeAddDTO {

    @ApiModelProperty(value = "名称",required = true)
    @Size(max = 32, message = "名称过长")
    @NotBlank(message = "名称不能为空")
    private String bankName;

    @ApiModelProperty(value = "图标",required = true)
    @NotBlank(message = "图标不能为空")
    private String bankImg;

    @ApiModelProperty(value = "背景色",required = true)
    @NotBlank(message = "背景色不能为空")
    private String color;

    @ApiModelProperty(value = "状态， 1：启用，0：禁用",required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

    private String code;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBankImg() {
        return bankImg;
    }

    public void setBankImg(String bankImg) {
        this.bankImg = bankImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BankType toBankType(){
        BankType bankType = new BankType();
        BeanUtils.copyProperties(this,bankType);
        return  bankType;
    }
}
