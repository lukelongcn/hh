package com.h9.api.model.vo;

import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.utils.DateUtil;
import lombok.Data;


/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Data
public class UserCouponVO {

    //使用范围
    public String wide = "";
    // 结束时间
    private String endTime;
    private String couponType = "";
    private String useType = "";
    private long goodsId ;

    public UserCouponVO(UserCoupon userCoupon, Goods goods) {
        this.endTime = DateUtil.formatDate(userCoupon.getCoupon().getEndTime(), DateUtil.FormatType.MINUTE);
        this.wide = "限 " + goods.getName() + " 适用";
        this.couponType = "免单劵";
        this.useType = "自营指定商品适用";
        if(goods != null){
            this.goodsId = goods.getId();
        }
    }
}
