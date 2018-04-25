package com.h9.store.modle.vo;

import lombok.Builder;
import lombok.Data;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/25
 */
@Data
@Builder
public class ModelGoodsVO {

    private Long id ;
    private String img ;
    private String name;
    private String price;
    private String desc;
    private String unit;
    // 订购数量
    private Long orderlimitNumberl;
}
