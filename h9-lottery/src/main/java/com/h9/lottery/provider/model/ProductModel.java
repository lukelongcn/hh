package com.h9.lottery.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.h9.common.db.entity.lottery.Product;

/**
 * Created with IntelliJ IDEA.
 *
 * ProductModel:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 20:04
 */
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

    public Product covert(){
        Product product = new Product();
        product.setCode(QueryCode);
        product.setSupplierName(ZGLB);
        product.setSupplierDistrict(GHQY);
        return product;
    }


    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getQueryCode() {
        return QueryCode;
    }

    public void setQueryCode(String queryCode) {
        QueryCode = queryCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getZGLB() {
        return ZGLB;
    }

    public void setZGLB(String ZGLB) {
        this.ZGLB = ZGLB;
    }

    public String getGHQY() {
        return GHQY;
    }

    public void setGHQY(String GHQY) {
        this.GHQY = GHQY;
    }
}
