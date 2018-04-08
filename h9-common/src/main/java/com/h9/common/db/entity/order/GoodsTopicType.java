package com.h9.common.db.entity.order;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/8.
 * 商品专题分类表。
 */
@Table(name = "goods_topic_type")
@Entity
@Data
public class GoodsTopicType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "border_img",columnDefinition = "varchar(300) comment '边框图片'")
    private String borderImg;

    @Column(name = "bg_color",columnDefinition = "varchar(50) comment '背景色'")
    private String bgColor;

    @Column(name = "title_color",columnDefinition = "varchar(50) comment '配置商品标题颜色'")
    private String titleColor;

    @Column(name = "price_color",columnDefinition = "varchar(50) comment '售价颜色'")
    private String priceColor;

    @Column(name = "name",columnDefinition = "varchar(300) comment '专题名'")
    private String name;

    @Column(name = "url",columnDefinition = "varchar(300) comment 'url'")
    private String url;
}
