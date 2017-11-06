package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.GoodsType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author: George
 * @date: 2017/11/6 14:55
 */
public class GoodAddDTO {
    @ApiModelProperty(value = "名称",required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "价格",required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price = new BigDecimal(0);

    @ApiModelProperty(value = "状态，1：上架，2：下架",required = true)
    @NotNull(message = "状态不能为空")
    private Integer status = 1;

    @ApiModelProperty(value = "描述",required = true)
    @NotEmpty(message = "描述不能为空")
    private String description;

    @ApiModelProperty(value = "库存",required = true)
    @NotNull(message = "库存不能为空")
    private Integer stock = 0;

    @ApiModelProperty(value = "商品类型id",required = true)
    @NotNull(message = "商品类型不能为空")
    private Long goodsTypeId;

    @Column(name = "didi_card_number",nullable = false,columnDefinition = "varchar(200) default '' COMMENT '滴滴卡兑换码' ")
    private String DiDiCardNumber;

    @Column(name = "img",nullable = false,columnDefinition = "varchar(256) default '' COMMENT '图片url' ")
    private String img;

    @Column(name = "v_coins_rate",columnDefinition = "DECIMAL(10,2) default 0 COMMENT 'V币兑换比例值'")
    private BigDecimal vCoinsRate = new BigDecimal(0);

    @Temporal(TIMESTAMP)
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '上架开始时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '上架结束时间'")
    private Date endTime;
}
