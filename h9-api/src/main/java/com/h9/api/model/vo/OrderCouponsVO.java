package com.h9.api.model.vo;

import com.h9.common.db.entity.user.UserCoupon;
import com.h9.common.utils.DateUtil;
import lombok.Data;

import javax.print.DocFlavor;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription: 当前订单可用优惠券</p>
 *
 * @author LiYuan
 * @Date 2018/4/8
 */
@Data
public class OrderCouponsVO {
    /**
      优惠结束时间
    */
    private String endTime;
    // 使用范围
    private String wide;

    public OrderCouponsVO(UserCoupon userCoupon){
        this.endTime = DateUtil.formatDate(userCoupon.getCouponId().getEndTime(), DateUtil.FormatType.MINUTE);
        this.wide = userCoupon.getCouponId().getGoodsId().getName();
    }
}
