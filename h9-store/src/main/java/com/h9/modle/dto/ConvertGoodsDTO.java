package com.h9.modle.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2017/11/29.
 */
@Data
@Accessors(chain = true)
public class ConvertGoodsDTO {

    @NotNull(message = "请选择地址")
    private Long addressId;

    @NotNull(message = "请选择您要兑换的数量")
    private Integer count;

    @NotNull(message = "请选择您要兑换的商品")
    private Long goodsId;

}
