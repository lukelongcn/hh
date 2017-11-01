package com.h9.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by itservice on 2017/10/27.
 */
public class Test {
    public static void main(String[] args) throws ParseException {


        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.basicAuthorization("user", "password").build();

        String code = RandomStringUtils.random(4, "0123456789");

        System.out.println(code);
    }
}
