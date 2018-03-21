package com.h9.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2018/1/15
 */
@ApiModel(description = "回复规则信息")
@Data
public class ReplyDTO {

    @ApiModelProperty(value = "规则名")
    @Size(min = 2 ,max = 120,message = "规则名必须在2到120个字符之间")
    private String orderName;
    /**
     * 匹配策略  规则类型
     */
    @ApiModelProperty(value = "规则类型")
    @NotBlank(message = "规则类型必填")
    private Integer matchStrategy;

    @ApiModelProperty(value = "关键词")
    @Size(max = 120)
    private String keyWord;

    // 回复类型
    @ApiModelProperty(value = "回复类型")
    @NotBlank(message = "回复类型必填")
    private String contentType;

    @ApiModelProperty(value = "回复内容")
    @NotBlank(message = "回复内容必填")
    private String content;

    // 正则表达式
    @ApiModelProperty(value = "正则表达式")
    private String matchRegex;

    @ApiModelProperty(value = "排序")
    @NotNull(message = "排序必填,只能是整数")
    private Integer sort;

    @ApiModelProperty(value = "状态 1使用 2 禁用")
    @NotNull(message = "状态 1使用 2 禁用")
    private Integer status;

}
