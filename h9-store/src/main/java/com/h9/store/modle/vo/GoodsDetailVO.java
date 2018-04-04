package com.h9.store.modle.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Created by itservice on 2017/11/28.
 */
@Data
@Builder
public class GoodsDetailVO {

    private Long id ;
    private String img = "";
    private String name = "";
    private String price = "";
    private String desc = "";
    private String tip;
    private int stock = 0;
    private String balance = "0";
    private String unit = "";
    // 用戶优惠券
    private String userCoupons;
    private Long userCouponsId;

}
