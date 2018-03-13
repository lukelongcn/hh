package com.h9.admin.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2018/1/15
 */
@Data
public class ReplyDTO {

    @Size(min = 2 ,max = 120)
    @NotBlank(message = "规则名必须在2到120个字符之间")
    private String orderName;
    /**
     * 匹配策略  规则类型
     */
    @NotBlank(message = "规则类型必填")
    private String matchStrategy;

    @Size(max = 120)
    @NotBlank(message = "关键词必填")
    private String keyWord;

    // 回复类型
    @NotBlank(message = "回复类型必填")
    private String contentType;

    @NotBlank(message = "回复内容必填")
    private String content;

    // 正则表达式
    private String matchRegex;

    @NotNull(message = "排序必填,只能是整数")
    private Integer sort;

    private Integer status;

}
