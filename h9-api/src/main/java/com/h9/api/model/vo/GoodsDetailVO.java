package com.h9.api.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Created by itservice on 2017/11/28.
 */
@Data
@Builder
public class GoodsDetailVO {

    private String img = "";
    private String name = "";
    private String price = "";
    private String desc = "";
    private String tip;

}
