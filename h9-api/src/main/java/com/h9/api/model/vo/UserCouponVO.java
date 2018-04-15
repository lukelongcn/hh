package com.h9.api.model.vo;

import com.h9.common.db.entity.coupon.Coupon;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


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
    private long goodsId;

    public UserCouponVO(UserCoupon userCoupon, Goods goods) {
        this.couponType = "免单劵";
        this.useType = "自营指定商品适用";
        if (goods != null) {
            if (userCoupon.getState() == UserCoupon.statusEnum.UN_USE.getCode()) {
                Coupon coupon = userCoupon.getCoupon();
                Date startTime = coupon.getStartTime();
                Date endTime = coupon.getEndTime();
                this.endTime = DateUtil.formatDate(startTime, DateUtil.FormatType.DOT_DAY)+"至"
                +DateUtil.formatDate(endTime, DateUtil.FormatType.DOT_DAY);
            }else{
                this.endTime = DateUtil.formatDate(userCoupon.getCoupon().getEndTime(), DateUtil.FormatType.DOT_MINUTE);

            }
            this.wide = "限 “" + goods.getName() + "” 适用";
            this.goodsId = goods.getId();
        }
//        if (StringUtils.isNotEmpty(userCoupon.getGoodsName())) {
//            this.wide = "限 “" + userCoupon.getGoodsName() + "” 适用";
//        }
    }
}
