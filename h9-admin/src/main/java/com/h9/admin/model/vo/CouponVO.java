package com.h9.admin.model.vo;

import com.h9.common.db.entity.coupon.Coupon;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.utils.DateUtil;
import lombok.Data;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/8
 */
@Data
public class CouponVO {
    private Long id;

    private String title;

    /**
     * 状态 1 未生效 0生效中 2已失效
     *
     * @see Coupon.statusEnum
     */
    private String status = "";

    private String startTime = "";

    private String endTime = "";

    private String wide = "";

    private int leftCount;

    private String goodsName = "";

    private long goodsId;

    private String couponType = "";

    private String createTime = "";

    private int askCount;


    public CouponVO() {

    }

    public CouponVO(Coupon coupon, Goods goods) {

        this.id = coupon.getId();
        this.title = coupon.getTitle();
        this.couponType = "免单劵";
        this.wide = "部分商品";
        this.leftCount = coupon.getLeftCount();
        UserCoupon.statusEnum findEnum = UserCoupon.statusEnum.findByCode(coupon.getStatus());
        if (findEnum != null) {
            this.status = findEnum.getDesc();
        }
        this.startTime = DateUtil.formatDate(coupon.getStartTime(), DateUtil.FormatType.MINUTE);
        this.endTime = DateUtil.formatDate(coupon.getEndTime(), DateUtil.FormatType.MINUTE);
        this.createTime = DateUtil.formatDate(coupon.getCreateTime(), DateUtil.FormatType.MINUTE);
        this.askCount = coupon.getAskCount();
        if(goods != null){
            this.goodsName = goods.getName();

            this.goodsId = goods.getId();
        }
    }
}
