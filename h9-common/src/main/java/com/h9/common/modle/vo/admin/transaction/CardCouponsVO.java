package com.h9.common.modle.vo.admin.transaction;

import com.h9.common.db.entity.account.CardCoupons;
import com.h9.common.modle.vo.admin.BasisVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @author: George
 * @date: 2017/12/2 16:03
 */
public class CardCouponsVO extends BasisVO{

    @ApiModelProperty(value = "id ")
    private Long id;

    @ApiModelProperty(value = "卡券号 ")
    private String no;

    @ApiModelProperty(value = "状态 ")
    private String status;

    @ApiModelProperty(value = "批次 ")
    private String batchNo;

    @ApiModelProperty(value = "导入卡券用户的id ")
    private Long userId;

    @ApiModelProperty(value = "发放卡券时间 ")
    protected Date grantTime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        if (StringUtils.isNotBlank(no) && no.length()>6) {
            StringBuilder stringBuilder = new StringBuilder(no.substring(0,3));
            for (int i=3;i<no.length()-3;i++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(no.substring(no.length()-3,no.length()));
            this.no = stringBuilder.toString();
        }else {
            this.no = no;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = CardCoupons.StatusEnum.getNameById(status);
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public CardCouponsVO() {
    }

    public CardCouponsVO(CardCoupons cardCoupons) {
        BeanUtils.copyProperties(cardCoupons, this);
    }

    public static Page<CardCouponsVO> toCardCouponsVO(Page<CardCoupons> cardCouponsPage) {
        if (cardCouponsPage == null) {
            return null;
        }
        return  cardCouponsPage.map(item -> new CardCouponsVO(item));
    }
}
