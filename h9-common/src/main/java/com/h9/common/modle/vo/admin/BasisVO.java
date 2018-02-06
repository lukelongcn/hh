package com.h9.common.modle.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * @author: George
 * @date: 2017/11/28 18:51
 */
public class BasisVO {
    @ApiModelProperty("创建时间")
    protected Date createTime ;

    @ApiModelProperty("更新时间")
    protected Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

   /* public ObjectVO(CardCoupons cardCoupons) {
        BeanUtils.copyProperties(cardCoupons, this);
    }

    public static Page<CardCouponsVO> toCardCouponsVO(Page<CardCoupons> cardCouponsPage) {
        if (cardCouponsPage == null) {
            return null;
        }
        return  cardCouponsPage.map(item -> new CardCouponsVO(item));
    }*/
}
