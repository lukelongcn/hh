package com.h9.admin.model.dto;

import com.h9.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/8.
 * 商品专题分类表。
 */
@Data
@ApiModel
public class GoodsTopicTypeVO extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ApiModelProperty("边框图")
    @Column(name = "border_img",columnDefinition = "varchar(300) comment '边框图片'")
    private String borderImg;

    @ApiModelProperty("背景颜色")
    @Column(name = "bg_color",columnDefinition = "varchar(50) comment '背景色'")
    private String bgColor;

    @ApiModelProperty("标题色")
    @Column(name = "title_color",columnDefinition = "varchar(50) comment '配置商品标题颜色'")
    private String titleColor;

    @ApiModelProperty("价格色")
    @Column(name = "price_color",columnDefinition = "varchar(50) comment '售价颜色'")
    private String priceColor;

    @ApiModelProperty("专题名")
    @Column(name = "name",columnDefinition = "varchar(300) comment '专题名'")
    private String name;

    @ApiModelProperty("url")
    @Column(name = "url",columnDefinition = "varchar(300) comment 'url'")
    private String url;
}
