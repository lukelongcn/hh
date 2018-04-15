package com.h9.api;


import com.h9.common.utils.DateUtil;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by itservice on 2017/11/2.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
//        System.out.println(DateUtil.getDate(new Date(),1, Calendar.DAY_OF_YEAR).getTime());
        System.out.println(DateUtil.getDate(new Date(),10, Calendar.MINUTE).getTime());
    }



}
