package com.h9.api;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by itservice on 2017/10/27.
 */
public class Test {
    public static void main(String[] args) throws ParseException {


        String code = RandomStringUtils.random(4, "0123456789");

        System.out.println(code);
    }
}
