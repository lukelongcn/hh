package com.h9.admin.model.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ln on 2018/4/8.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class GoodsTopicModuleVO {
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("商品数量")
    private String goodsCount;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("专题模块Id")
    private Long topicModuleId;
}
