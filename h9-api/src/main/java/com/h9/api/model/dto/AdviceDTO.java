package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/3
 */
@Data
public class AdviceDTO {

    @NotBlank(message = "请填写意见")
    @Size(min = 2,max = 200,message = "意见过长")
    private String advice;

    @Size(max = 3,message = "图片上传最多三张")
    private List<String> adviceImgList;

    @Size(min = 2,max = 30,message = "联系方式长度2-30个字符")
    private String connect;

    private Integer anonymous;

    private Integer adviceType;

}
