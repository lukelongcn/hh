package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.order.Goods;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author: George
 * @date: 2017/11/6 14:55
 */
public class GoodsAddDTO {
    @ApiModelProperty(value = "名称",required = true)
    @NotBlank(message = "名称不能为空")
    @Size(max = 64,message = "名称过长")
    private String name;

    @ApiModelProperty(value = "编码",required = true)
    @NotBlank(message = "编码不能为空")
    @Size(max = 30,message = "编码长度不能超过30位")
    private String code;

    @ApiModelProperty(value = "商品类型id",required = true)
    @NotNull(message = "商品类型不能为空")
    private Long goodsTypeId;

    @ApiModelProperty(value = "商品单位",required = true)
    @NotNull(message = "商品单位不能为空")
    private String unit;

    @ApiModelProperty(value = "图片",required = true)
    @NotBlank(message = "图片不能为空")
    private String img;

    @ApiModelProperty(value = "原价",required = true)
    @NotNull(message = "原价不能为空")
    private BigDecimal realPrice = new BigDecimal(0);

    @ApiModelProperty(value = "价格",required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price = new BigDecimal(0);

    @ApiModelProperty(value = "V币兑换比例值",required = true)
    @NotNull(message = "V币兑换比例值不能为空")
    private BigDecimal vCoinsRate = new BigDecimal(0);

    @ApiModelProperty(value = "描述",required = true)
    @NotBlank(message = "描述不能为空")
    //@Size(max = 256,message = "描述过长")
    private String description;

    @ApiModelProperty(value = "上架开始时间",required = true)
    //@NotNull(message = "上架开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "上架结束时间",required = true)
    //@NotNull(message = "上架结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "库存",required = true)
    @NotNull(message = "库存不能为空")
    private Integer stock = 0;

    @ApiModelProperty(value = "状态，1：上架，2：下架",required = true)
    @NotNull(message = "状态不能为空")
    private Integer status = 1;

    @ApiModelProperty(value = "顺序",required = true)
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Long goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getvCoinsRate() {
        return vCoinsRate;
    }

    public void setvCoinsRate(BigDecimal vCoinsRate) {
        this.vCoinsRate = vCoinsRate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Goods toGoods(){
        Goods goods = new Goods();
        BeanUtils.copyProperties(this,goods);
        return goods;
    }
}
