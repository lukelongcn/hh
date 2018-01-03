package com.h9.lottery.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.h9.common.db.entity.Product;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * ProductModel:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 20:04
 */
@Data
public class ProductModel {
    @JsonProperty("State")
    private int State;
    @JsonProperty("Msg")
    private String Msg;
    @JsonProperty("QueryCode")
    private String QueryCode;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("ZGLB")
    private String ZGLB;
    @JsonProperty("GHQY")
    private String GHQY;
    @JsonProperty("PlanOID")
    private String planId;

    public Product covert(){
        Product product = new Product();
        product.setCode(QueryCode);
        product.setSupplierName(ZGLB);
        product.setSupplierDistrict(GHQY);
        product.setPlanId(planId);
        return product;
    }


}
