package com.h9.common.modle.dto.transaction;

import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/12/2 14:38
 */
public class CardCouponsDTO extends PageDTO{
    @ApiModelProperty(value = "商品id")
    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "券号")
    private String no;

    @ApiModelProperty(value = "批次")
    private String batchNo;

    @ApiModelProperty(value = "状态,1:正常，2:已使用,3 禁用")
    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
