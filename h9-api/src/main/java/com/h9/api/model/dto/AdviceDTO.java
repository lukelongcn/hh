package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2018/1/3
 */
public class AdviceDTO {

    @NotBlank(message = "请填写意见")
    @Size(min = 2,max = 200,message = "联系方式长度2-30个字符")
    private String advice;

    private String adviceImg1;

    private String adviceImg2;

    private String adviceImg3;


    @Size(min = 2,max = 30,message = "联系方式长度2-30个字符")
    private String connect;

    private Integer anonymous;

    private String adviceType;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getAdviceImg1() {
        return adviceImg1;
    }

    public void setAdviceImg1(String adviceImg1) {
        this.adviceImg1 = adviceImg1;
    }

    public String getAdviceImg2() {
        return adviceImg2;
    }

    public void setAdviceImg2(String adviceImg2) {
        this.adviceImg2 = adviceImg2;
    }

    public String getAdviceImg3() {
        return adviceImg3;
    }

    public void setAdviceImg3(String adviceImg3) {
        this.adviceImg3 = adviceImg3;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }
}
