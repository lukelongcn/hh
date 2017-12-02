package com.h9.common.modle.vo.admin.transaction;

import com.h9.common.db.entity.CardCoupons;
import com.h9.common.modle.vo.admin.BasisVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

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
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = CardCoupons.;
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
        BeanUtils.copyProperties();
    }
}
