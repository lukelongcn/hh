package com.h9.common.db.entity;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.h9.common.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * description: 酒店
 */
@Entity
@Table(name = "hotel")
@Getter
@Setter
@Accessors(chain = true)
public class Hotel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "images",columnDefinition = "varchar(255) COMMENT '酒店图片'")
    private String images;

    @Column(name = "grade",columnDefinition = "float comment '酒店评分'")
    private Float grade;

    @Column(name = "detail_address",columnDefinition = "varchar(255) comment '酒店地址'")
    private String detailAddress;

    @Column(name = "tips",columnDefinition = "varchar(255) comment '预定提示'")
    private String tips;

    @Column(name = "hotel_info",columnDefinition = "varchar(255) comment '酒店介绍 url'")
    private String hotelInfo;

    @Column(name= "discount",columnDefinition = "float comment '折扣'")
    private Float discount;

    @Column(name = "mix_consumer",columnDefinition = "decimal(10,2) comment '最低消费'")
    private BigDecimal mixConsumer;

    @Column(name = "city",columnDefinition = "varchar(255) comment '城市'")
    private String city;

    @Column(name = "hotel_name",columnDefinition = "varchar(255) comment '酒店名'")
    private String hotelName;

    public void setImages(String[] images) {
        if (images != null && images.length > 0) {
            this.images = JSONObject.toJSONString(images);
        }
    }
}
