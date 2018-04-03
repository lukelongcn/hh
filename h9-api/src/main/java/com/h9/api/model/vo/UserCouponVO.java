package com.h9.api.model.vo;

import com.h9.common.db.entity.user.UserCoupon;
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
    public String wide;

    // 结束时间
    private String endTime;

    public UserCouponVO(UserCoupon userCoupon){
        this.endTime = DateUtil.formatDate(userCoupon.getCouponId().getEndTime(), DateUtil.FormatType.MINUTE);
        this.wide = userCoupon.getCouponId().getGoodsId().getName();
    }
}
