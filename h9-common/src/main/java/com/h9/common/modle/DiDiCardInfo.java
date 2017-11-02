package com.h9.common.modle;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/11/2.
 */
public class DiDiCardInfo {
    private String price;
    private String stock;


    public DiDiCardInfo(BigDecimal price, Long stock) {
        this.price = price.toString();
        this.stock = stock+"";
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {

        this.stock = stock;
    }

    @Override
    public String toString() {
        return "DiDiCardInfo{" +
                "price='" + price + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
}
