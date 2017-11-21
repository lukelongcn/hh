package com.h9.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by itservice on 2017/11/18.
 */
public class MoneyUtils {

    private MoneyUtils(){}

    public static  String formatMoney(BigDecimal money){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(money);
    }

    public static String formatMoney(BigDecimal money,String pattern){
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(money);
    }

    public static void main(String[] args) {
        BigDecimal de = new BigDecimal("22.00");
        DecimalFormat format = new DecimalFormat("0");

        System.out.println(format.format(de));
    }
}
