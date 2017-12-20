package com.h9.admin.model.dto.transaction;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/12/4 10:46
 */
public class CardCouponsListAddDTO {
    @ApiModelProperty(value = "商品id")
    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "卡券号")
    @NotBlank(message = "数据不能为空")
    private String nos;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getNos() {
        return nos;
    }

    public void setNos(String nos) {
        this.nos = nos;
    }
}
