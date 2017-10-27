package com.h9.api;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.provider.SMService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.repo.SMSLogReposiroty;
import com.h9.common.utils.MD5Util;
import com.wedo.server.api.sdk.Service;
import com.wedo.server.api.sdk.rest.model.AuthUser;
import com.wedo.server.api.sdk.rest.model.PostResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Resource
    private SMService smService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Test
    public void TestMsm() {
        String mobile = "17673140753";
        Result result = smService.sendSMS(mobile);
        System.out.println(result);
    }


    @Resource
    private RedisBean redisBean;
    @Test
    public void redisTest(){
        String key = String.format("h9:sms:count:%s", "17673140753");
        redisBean.setStringValue(key,"0");
    }

    @Test
    public void test(){
        PostResult<AuthUser> postResult = Service.getInstance().loginFromNoPassword("17673140753");
        System.out.println(postResult);
    }
}
