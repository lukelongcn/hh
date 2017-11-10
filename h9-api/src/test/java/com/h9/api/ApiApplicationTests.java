package com.h9.api;

//import com.h9.api.provider.ChinaPayService;
//import com.h9.api.provider.MobileRechargeService;

import com.h9.api.interceptor.LoginAuthInterceptor;
import com.h9.api.provider.SMService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.GoodsDIDINumber;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.*;
import com.h9.common.db.entity.GoodsType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
//    @Test
//    public void TestMsm() {
//        String mobile = "17673140753";
//        Result result = smService.sendSMS(mobile);
//        System.out.println(result);
//    }

    @Resource
    private OrderItemReposiroty orderItemReposiroty;

    @Resource
    private RedisBean redisBean;

    //    @Test
    public void redisTest() {
        String key = String.format("h9:sms:count:%s", "17673140753");
        redisBean.setStringValue(key, "0");
    }

    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;

    //    @Test
    public void test() {
        String key = RedisKey.getTokenUserIdKey("ff444b6d-ac89-41a3-8e8b-de3c59fd6d26");
        String stringValue = redisBean.getStringValue(key);
        redisBean.setStringValue(key, "");

        System.out.println(stringValue);
    }

    //    @Resource
//    private MobileRechargeService mobileRechargeService;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;

    @Test
//    @Test
    public void mobileRecharge() {


    }

    @Resource
    GoodsDIDINumberRepository goodsDIDINumberRepository;

    @Test
    public void didiCardInit() {

        GoodsType goodsType = goodsTypeReposiroty.findOne(2L);
        for (int i = 0; i < 200; i++) {

            GoodsDIDINumber goodsDIDINumber = new GoodsDIDINumber();
            goodsDIDINumber.setDidiNumber(UUID.randomUUID().toString());
            goodsDIDINumber.setGoodsId(1310L);
            goodsDIDINumber.setStatus(1);
            goodsDIDINumberRepository.save(goodsDIDINumber);
        }
    }

    @Value("${chinaPay.merId}")
    private String merId;

    //    @Resource
//    private ChinaPayService chinaPayService;
    @Test
    public void testcp() {

        String merId = "808080211881410";
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String merDate = format.format(new Date());
        String merSeqId = "8";
        String cardNo = "6210984280001561104";
        String usrName = "李圆";
        String openBank = "中国邮政储蓄银行";
        String prov = "江西";
        String city = "赣州";
        String transAmt = "101";
        String purpose = "提现";
        String version = "20151207";
        String signFlag = "1";
        String termType = "7";

//        ChinaPayService.PayParam payParam = new ChinaPayService.PayParam(merSeqId, cardNo, usrName, openBank, prov, city, transAmt, signFlag, purpose);
//        Result result = chinaPayService.signPay(payParam);
//        System.out.println(result);
    }


    @Resource
    private OrdersRepository ordersReposiroty;

    @Test
    public void asys() {
        Future<List<Orders>> allAsy = ordersReposiroty.findAllAsy();
        try {
            List<Orders> orders = allAsy.get();
            System.out.println(orders.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testKey() {
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream("MerPrK_808080211881410_20171102154758.key");
//        PrivateKey key = new PrivateKey();
//        System.out.println(new File("/").getAbsolutePath());
//        boolean buildOK = key.buildKey(merId, 0, path);
//        System.out.println(is == null);
    }
}
