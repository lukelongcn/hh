package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.order.Goods;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;


/**
 * @author: George
 * @date: 2017/11/6 14:55
 */
public class GoodsEditDTO extends GoodsAddDTO {
    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Goods toGoods(){
        Goods goods = new Goods();
        BeanUtils.copyProperties(this,goods);
        return goods;
    }
}
