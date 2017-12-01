package com.h9;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by itservice on 2017/11/30.
 */
public class TestOrder {

    private static final String host = "http://localhost:6305/h9/api";
    private RestTemplate restTemplate = new RestTemplate();


    @Test
    public void test(){
        String token = Login();
        System.out.println(token);
    }

    public String Login(){
        sendSMS();
        String tel = "17673140753";
        String url = host+"/user/phone/login";

        Map<String, String> map = new HashMap<>();
        map.put("code", "0000");
        map.put("phone", "17673140753");

        ResponseEntity<String> response = restTemplate.postForEntity( url, JSONObject.toJSON(map), String.class );

        JSONObject resObj = JSONObject.parseObject(response.getBody());

        Object data = resObj.get("data");
        Object token = JSONObject.parseObject((String) data).get("token");
        return token.toString();
    }


    public  void sendSMS(){

        String url = host + "/user/register/17673140753";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
    }
}
