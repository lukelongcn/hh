package com.h9.api;

import com.h9.api.interceptor.LoginAuthInterceptor;
import com.h9.api.provider.MobileRechargeService;
import com.h9.api.provider.SMService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.modle.DiDiCardInfo;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import com.h9.common.db.repo.SMSLogReposiroty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  ApiApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Resource
    private SMService smService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
//    @Test
//    public void TestMsm() {
//        String mobile = "17673140753";
//        Result result = smService.sendSMS(mobile);
//        System.out.println(result);
//    }


    @Resource
    private RedisBean redisBean;
//    @Test
    public void redisTest(){
        String key = String.format("h9:sms:count:%s", "17673140753");
        redisBean.setStringValue(key,"0");
    }

    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;
//    @Test
    public void test(){
        String key = RedisKey.getTokenUserIdKey("ff444b6d-ac89-41a3-8e8b-de3c59fd6d26");
        String stringValue = redisBean.getStringValue(key);
        redisBean.setStringValue(key,"");

        System.out.println(stringValue);
    }

    @Resource
    private MobileRechargeService mobileRechargeService;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;
    @Test
//    @Test
    public void mobileRecharge(){


    }

    @Test
    public void didiCardInit(){

        GoodsType goodsType = goodsTypeReposiroty.findOne(2L);
        for (int i = 0; i < 200; i++) {
            Goods goods = new Goods();
            goods.setDescription("滴滴卡兑换");
            goods.setName("滴滴卡兑换");
            goods.setPrice(new BigDecimal(30));
            goods.setRealPrice(new BigDecimal(30));
            goods.setDiDiCardNumber(UUID.randomUUID().toString());
            goods.setStatus(1);
            goods.setGoodsType(goodsType);
            goodsReposiroty.save(goods);
        }
    }

    @Test
    public void test2(){
        List<DiDiCardInfo> realPriceAndStock = goodsReposiroty.findRealPriceAndStock();
        System.out.println(realPriceAndStock);
    }

}
