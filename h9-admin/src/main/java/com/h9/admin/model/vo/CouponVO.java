package com.h9.admin.model.vo;

import com.h9.common.db.entity.order.Coupon;
import com.h9.common.utils.DateUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

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
     * @see Coupon.statusEnum
     */
    private int status;

    private String startTime;

    private String endTime;

    private String wide;

    private int leftCount;

    private String goodsName;

    private String couponType;

    private String createTime;

    public CouponVO(){

    }
    public CouponVO(Coupon coupon){

        this.id = coupon.getId();
        this.title = coupon.getTitle();
        this.couponType = coupon.getCouponType();
        this.wide = "部分商品";
        this.leftCount = coupon.getLeftCount();
        this.status = coupon.getStatus();
        this.startTime = DateUtil.formatDate(coupon.getStartTime(), DateUtil.FormatType.MINUTE);
        this.endTime = DateUtil.formatDate(coupon.getEndTime(), DateUtil.FormatType.MINUTE);
        this.createTime = DateUtil.formatDate(coupon.getCreateTime(), DateUtil.FormatType.MINUTE);
        this.goodsName = coupon.getGoodsId().getName();

    }
}
