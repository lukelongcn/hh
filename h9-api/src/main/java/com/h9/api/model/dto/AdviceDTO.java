package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2018/1/3
 */
public class AdviceDTO {

    @NotBlank(message = "请填写意见")
    @Size(min = 2,max = 200,message = "联系方式长度2-30个字符")
    private String advice;

    @Size(max = 3,message = "图片上传最多三张")
    private List<String> adviceImgList;

    @Size(min = 2,max = 30,message = "联系方式长度2-30个字符")
    private String connect;

    private Integer anonymous;

    private Integer adviceType;

    public List<String> getAdviceImgList() {
        return adviceImgList;
    }

    public void setAdviceImgList(List<String> adviceImgList) {
        this.adviceImgList = adviceImgList;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
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

    public Integer getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(Integer adviceType) {
        this.adviceType = adviceType;
    }
}
