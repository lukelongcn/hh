package com.h9.lottery.provider.model;

import com.h9.common.db.entity.Product;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * ProductModel:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 20:04
 */
public class ProductModel {
    private int State;
    private String Msg;
    private String QueryCode;
    private String Name;
    private String ZGLB;
    private String GHQY;

    public Product covert(){
        Product product = new Product();
        product.setCode(QueryCode);
        product.setName(Name);
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
