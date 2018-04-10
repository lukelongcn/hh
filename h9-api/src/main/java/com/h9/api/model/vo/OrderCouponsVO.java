package com.h9.api.model.vo;

import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.utils.DateUtil;
import lombok.Data;

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
     * 优惠结束时间
     */
    private String endTime;
    // 使用范围
    private String wide = "";
    private String useType = "";
    private long id;
    private Long goodsId;

    public OrderCouponsVO(UserCoupon userCoupon, Goods goods) {
        this.endTime = DateUtil.formatDate(userCoupon.getCoupon().getEndTime(), DateUtil.FormatType.MINUTE);
        this.wide = goods.getName();
        this.useType = "自营指定商品";
        this.id = userCoupon.getId();
        this.goodsId = goods.getId();
    }
}
