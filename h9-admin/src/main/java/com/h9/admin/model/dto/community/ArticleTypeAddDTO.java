package com.h9.admin.model.dto.community;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/5 17:45
 */
public class ArticleTypeAddDTO {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型名称'")
    @ApiModelProperty(value = "动作",required = true)
    @NotEmpty(message = "动作")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型标识'")
    @ApiModelProperty(value = "动作",required = true)
    @NotEmpty(message = "动作")
    private String code;


    @ApiModelProperty(value = "是否启用,0:禁用,1:启用",required = true)
    @NotNull(message = "是否启用不能为空")
    private Integer enable;
}
