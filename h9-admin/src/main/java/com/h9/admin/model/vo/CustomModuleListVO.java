package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ln on 2018/4/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("列表数据对象")
public class CustomModuleListVO {

    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("模块名")
    private String name;
    @ApiModelProperty("模块类型")
    private String type;
    @ApiModelProperty("商品数量")
    private int goodsNumber;
    @ApiModelProperty("创建时间")
    private String createTime = "";
    @ApiModelProperty("更新时间")
    private String updateTime = "";
}
