package com.h9.api.model.vo.coupon;

import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ln on 2018/4/18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareVO {
    private String title;// 分享标题
    private String desc;// 分享描述
    private String link; // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
    private String imgUrl; // 分享图标

    public ShareVO(UserCoupon userCoupon, Goods goods,String link,String imgUrl) {
        this.title = userCoupon.getGoodsName() + " 免单劵|立即领取";
        this.desc = "你的好友送你一张 \"" + userCoupon.getGoodsName() + "\"免单劵，快来领取吧，小心被别人领完哟";
        this.link = link;
        this.imgUrl = imgUrl;
    }
}
