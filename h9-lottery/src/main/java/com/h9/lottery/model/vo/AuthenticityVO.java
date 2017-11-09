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
    private String supplierName;
    private String supplierDistrict;
    private String lastQueryTime;
    private String lastQueryAddress;
    private BigDecimal queryCount = new BigDecimal(0);

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierDistrict() {
        return supplierDistrict;
    }

    public void setSupplierDistrict(String supplierDistrict) {
        this.supplierDistrict = supplierDistrict;
    }

    public String getLastQueryTime() {
        return lastQueryTime;
    }

    public void setLastQueryTime(String lastQueryTime) {
        this.lastQueryTime = lastQueryTime;
    }

    public BigDecimal getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(BigDecimal queryCount) {
        this.queryCount = queryCount;
    }

    public String getLastQueryAddress() {
        return lastQueryAddress;
    }

    public void setLastQueryAddress(String lastQueryAddress) {
        this.lastQueryAddress = lastQueryAddress;
    }
}

