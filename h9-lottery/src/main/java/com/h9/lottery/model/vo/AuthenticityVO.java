package com.h9.lottery.model.vo;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * AuthenticityVO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 19:51
 */
public class AuthenticityVO {

    private String productName;
    private String type;
    private String degrees;
    private String unit;
    private BigDecimal queryCount = new BigDecimal(0);

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(BigDecimal queryCount) {
        this.queryCount = queryCount;
    }
}
