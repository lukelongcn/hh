package com.h9.admin.model.dto.topic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Ln on 2018/4/8.
 */
@Data
@ApiModel
public class GoodsTopicDTO {
    @ApiModelProperty(value = "模块图片")
    private String img;

}
