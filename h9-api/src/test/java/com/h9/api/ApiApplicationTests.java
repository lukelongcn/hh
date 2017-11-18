package com.h9.api;

//import com.h9.api.provider.ChinaPayService;
//import com.h9.api.provider.MobileRechargeService;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.interceptor.LoginAuthInterceptor;
import com.h9.api.provider.MobileRechargeService;
import com.h9.api.provider.SMSProvide;
import com.h9.common.base.Result;
import com.h9.common.common.MailService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;

import com.h9.common.utils.MD5Util;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {


    ////@Test
    public void contextLoads() {

    }

    @Resource
    private SMSProvide smService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
//    ////@Test
//    public void TestMsm() {
//        String mobile = "17673140753";
//        Result result = smService.sendSMS(mobile);
//        System.out.println(result);
//    }

    @Resource
    private OrderItemReposiroty orderItemReposiroty;

    @Resource
    private RedisBean redisBean;

    //    ////@Test
    public void redisTest() {
        String key = String.format("h9:sms:count:%s", "17673140753");
        redisBean.setStringValue(key, "0");
    }

    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;

    //    ////@Test
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

    ////@Test
//    ////@Test
    public void mobileRecharge() {


    }

    @Resource
    GoodsDIDINumberRepository goodsDIDINumberRepository;

    ////@Test
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
    ////@Test
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

    ////@Test
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


    ////@Test
    public void testKey() {
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream("MerPrK_808080211881410_20171102154758.key");
//        PrivateKey key = new PrivateKey();
//        System.out.println(new File("/").getAbsolutePath());
//        boolean buildOK = key.buildKey(merId, 0, path);
//        System.out.println(is == null);
    }

    @Resource
    OrdersRepository ordersRepository;

    //@Test
    public void testSpecification() {

    }

     Logger logger = Logger.getLogger(ApiApplicationTests.class);


    @Resource
    private UserRepository userRepository;

    @Resource
    private UserAccountRepository userAccountRepository;

    @Resource
    UserExtendsRepository userExtendsRepository;


    //@Test
    public void saveUser(){
        User user = initUserInfo("13066886409");
        int loginCount = user.getLoginCount();
        user.setLoginCount(++loginCount);
        user.setLastLoginTime(new Date());
        user = userRepository.saveAndFlush(user);
        logger.debugv(JSONObject.toJSONString(user));

        logger.debugv("-----------------------------------");
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(user.getId());
        logger.debugv(JSONObject.toJSONString(userAccount));
        userAccount = userAccountRepository.saveAndFlush(userAccount);
        logger.debugv(JSONObject.toJSONString(userAccount));

        logger.debugv("-----------------------------------");
        UserExtends userExtends = new UserExtends();
        userExtends.setUserId(user.getId());
        logger.debugv(JSONObject.toJSONString(userExtends));
        userExtendsRepository.save(userExtends);
        logger.debugv(JSONObject.toJSONString(userExtends));

    }

    @Resource
    private GlobalPropertyRepository globalPropertyRepository;


    /**
     * description: 初化一个用户，并返回这个用户对象
     */
    private User initUserInfo(String phone) {
        if (phone == null) return null;
        User user = new User();
        user.setAvatar("");
        user.setLoginCount(0);
        user.setPhone(phone);
        CharSequence charSequence = phone.subSequence(4, 8);
        user.setNickName(phone.replace(charSequence, "****"));
        user.setLastLoginTime(new Date());
        GlobalProperty defaultHead = globalPropertyRepository.findByCode("defaultHead");
        user.setAvatar(defaultHead.getVal());
        return user;
    }


    @Resource
    UserBankRepository userBankRepository;
    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordReposiroty;
    @Test
    public void TestAccount(){

//        redisBean.setStringValue("sms:code:count:5:12345678912", "0");
//        redisBean.setStringValue("h9:sms:code:errorCount:userId:9:type:5", "0");
    }

    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private MailService mailService;


}



