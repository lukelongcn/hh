package com.h9.admin.model.vo;

import com.h9.common.db.entity.coupon.Coupon;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.utils.DateUtil;
import lombok.Data;

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

    private boolean canSend = true;

    private boolean canEdit = true;

    public CouponVO() {

    }

    public CouponVO(Coupon coupon, Goods goods) {

        this.id = coupon.getId();
        this.title = coupon.getTitle();
        this.couponType = "免单劵";
        this.wide = "部分商品";
        this.leftCount = coupon.getLeftCount();

        Date now = new Date();
        Date startTime = coupon.getStartTime();
        Date endTime = coupon.getEndTime();
        if (startTime.after(now)) {
            this.status = "未生效";
        } else if (now.after(endTime)) {
            this.status = "已失效";
            this.canSend = false;
            this.canEdit = false;
        } else {
            this.status = "生效中";
        }

        int sendFlag = coupon.getSendFlag();
        if (sendFlag == 2) {
            this.canEdit = false;
        }
        this.startTime = DateUtil.formatDate(coupon.getStartTime(), DateUtil.FormatType.MINUTE);
        this.endTime = DateUtil.formatDate(coupon.getEndTime(), DateUtil.FormatType.MINUTE);
        this.createTime = DateUtil.formatDate(coupon.getCreateTime(), DateUtil.FormatType.MINUTE);
        this.askCount = coupon.getAskCount();
        if (goods != null) {
            this.goodsName = goods.getName();
            this.goodsId = goods.getId();
        }
    }
}
