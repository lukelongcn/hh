package com.h9.admin.model.dto.topic;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Ln on 2018/4/8.
 */
@ApiModel
@Data
public class EditGoodsTopicTypeDTO {

   @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("边框图片")
    private String borderImg;

    @ApiModelProperty("背景色")
    private String bgColor;

    @ApiModelProperty("标题颜色")
    private String titleColor;

    @ApiModelProperty("价格颜色")
    private String priceColor;

    @NotEmpty(message = "请填写专题分类的名字")
    @ApiModelProperty("名字")
    private String name;

}
