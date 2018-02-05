package com.h9.api.model.vo;

import com.h9.common.db.entity.order.Goods;
import com.h9.common.utils.MoneyUtils;
import lombok.Data;

/**
 * Created by itservice on 2017/11/28.
 */
@Data
public class GoodsListVO {
    private String img;
    private String name;
    private String price;
    // 单位
    private String unit;
    private Long id;

    public GoodsListVO(){}

    public GoodsListVO(Goods goods){
        this.img = goods.getImg();
        this.name = goods.getName();
        this.price = MoneyUtils.formatMoney(goods.getRealPrice());
        this.id = goods.getId();
        this.unit = goods.getUnit();
    }
}
