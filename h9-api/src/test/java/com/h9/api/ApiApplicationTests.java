package com.h9.api;



import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.interceptor.LoginAuthInterceptor;
import com.h9.api.model.dto.Areas;
import org.apache.commons.net.util.Base64;

import com.h9.api.provider.SMSProvide;
import com.h9.api.provider.SuNingProvider;
import com.h9.api.provider.model.WithdrawDTO;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.FileService;
import com.h9.api.service.UserService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.common.MailService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {


    @Test
    public void test2222() {
        Address address = new Address();
        //address.setUserId(userId);
        address.setName("12312");
        address.setPhone("13456458529");

        //String provinceName = chinaRepository.findName(8);
        // String cityName = chinaRepository.findName(addressDTO.getCid());
        //String areaName = chinaRepository.findName(addressDTO.getAid());
        address.setProvince("312312");
        address.setCity("asdas");
        address.setDistict("2312");
        //设值地址id
        //address.setPid();
        //address.setCid(addressDTO.getCid());
        //address.setAid(addressDTO.getAid());
        //设值详细地址
        address.setAddress("3131");
        // 设置是否为默认地址
        // if(addressDTO.getDefaultAddress() == 1){
        //    addressRepository.updateDefault(userId);
        //  }
        address.setDefaultAddress(1);

        // 使用状态设为开启
        address.setStatus(1);
        address = addressRepository.saveAndFlush(address);

        logger.debug(address.getId());
    }
    private String accessKey= "9HVEtM7CFBFDTivYyrIci1Y9XV5K-hIWa2vIxRLO";
    String secretKey = "HvcdEp5BIZFJkMwwarStRiRHOCfm9KjoxngXFljT";
    private String bucket = "huanlezhijia";
    @Value("${qiniu.img.path}")
    private String imgPath;

    @Resource
    ChinaRepository chinaRepository;

    @Resource
    private FileService fileService;

    @Test
    public void testUPloadFile() {
        File file = new File("C:\\Users\\itservice\\Pictures\\9abcc0b5ca303bbd57bd12393951c72b.jpg");
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(new FileInputStream(file), key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println("上传成功"+imgPath + putRet.key);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findFromDb() {
        //从数据库获取数据
        Long startTime = System.currentTimeMillis();
        List<China> allProvices = chinaRepository.findAllProvinces();

        List<Areas> areasList = allProvices.stream().map(Areas::new).collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        logger.debugv("时间" + (end - startTime));
//        存储到redis
        redisBean.setObject(RedisKey.addressKey, areasList);

    }


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


    ////@Test
    public void didiCardInit() {

        GoodsType goodsType = goodsTypeReposiroty.findOne(2L);
        for (int i = 0; i < 200; i++) {

            CardCoupons cardCoupons = new CardCoupons();
            cardCoupons.setNo(UUID.randomUUID().toString());
            cardCoupons.setGoodsId(1310L);
            cardCoupons.setStatus(1);
            cardCouponsRepository.save(cardCoupons);
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

    @Test
    public void testSpecification() {
        String userIdKey = redisBean.getStringValue(RedisKey.getTokenUserIdKey("a7febb21-366d-4fd2-8f18-2498bea2a727"));
        System.out.println(userIdKey);

    }

    Logger logger = Logger.getLogger(ApiApplicationTests.class);


    @Resource
    private UserRepository userRepository;

    @Resource
    private UserAccountRepository userAccountRepository;

    @Resource
    UserExtendsRepository userExtendsRepository;


    //@Test
    public void saveUser() {
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
        return user;
    }


    @Resource
    UserBankRepository userBankRepository;
    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordReposiroty;

    @Test
    public void TestAccount() {

        redisBean.setStringValue("sms:code:count:4:18770812669", "0");

        String stringValue = redisBean.getStringValue("sms:code:count:4:18770812669");
        System.out.println(stringValue);
        String tel = "15970051786";
        String smsCodeKey = RedisKey.getSmsCodeKey(tel, 6);
        String stringValue1 = redisBean.getStringValue(smsCodeKey);
        System.out.println(stringValue1);

    }

    @Resource
    CardCouponsRepository cardCouponsRepository;

    @Test
    public void cardsGenerator() {

        for (int i = 0; i < 10000; i++) {
            if (i / 1000 == 0) {
                System.out.println(i);
            }
            CardCoupons cardCoupons = new CardCoupons();
            cardCoupons.setBatchNo("20170904");
            cardCoupons.setNo(UUID.randomUUID().toString().substring(0, 10));
            cardCoupons.setGoodsId(1L);
            cardCoupons.setMoney(new BigDecimal(20));
            cardCoupons.setStatus(1);

            cardCouponsRepository.save(cardCoupons);
//            cardCoupons.setBatchNo();
        }
    }

    @Resource
    private ConfigService configService;
    @Resource
    private BankBinRepository bankBinRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private MailService mailService;

    @Resource
    private BannerRepository bannerRepository;

    @Resource
    private AddressRepository addressRepository;

    @Test
    public void test22() {
        String errorCodeCountKey = RedisKey.getErrorCodeCountKey(14L, SMSTypeEnum.BIND_BANKCARD.getCode());
        String value = redisBean.getStringValue(errorCodeCountKey);
        System.out.println(value);
    }

    @Resource
    private BalanceFlowRepository balanceFlowRepository;

    @Test
    public void testPerformance() {

        long start = System.currentTimeMillis();
        int page = 0;
        int limit = 10;
        PageRequest pageRequest = balanceFlowRepository.pageRequest(page, limit);
        Page<BalanceFlow> balanceFlows = balanceFlowRepository.findByBalance(13315L, pageRequest);
        PageResult<BalanceFlow> flowPageResult = new PageResult<>(balanceFlows);
        Map iconMap = configService.getMapConfig(ParamConstant.BALANCE_FLOW_IMG);

        long end = System.currentTimeMillis();
        logger.info("time :　" + (end - start));
    }

    @Resource
    private LotteryRepository lotteryRepository;

    @Resource
    private WeChatProvider weChatProvider;

    @Resource
    private UserService userService;
    @Test
    public void testRecharge(){
//        String ticket = userService.getTicket("6_mjk2qXu_N7I4VNHR4lS1sPzjwcHTzaDATP2hQfXv-KeBklcEJvzrbbj5i" +
//                "d9ZmwI5zUpKajPgmmrE4EmrvSo-kYaXi3jMmlAiLUt0MCyedbJGqs6vbjtoVU2DVmwLJPfAIAPNT", userId);
//        System.out.println(ticket);
    }

    @Test
    public  void testCreateMenu(){
        weChatProvider.createMenu();
    }

    @Resource
    private SuNingProvider suNingProvider;

    @Resource
    private UserBankRepository bankRepository;


    @Test
    public void  testWithdraw(){
        UserBank userBank = bankRepository.findOne(231L);
        if(userBank==null){
            logger.debugv("用户银行卡");
        }
        WithdrawDTO withdrawDTO = new WithdrawDTO(userBank,new BigDecimal(0.01),1l,"1");
        Result withdraw = suNingProvider.withdraw(withdrawDTO);
        logger.debugv(JSONObject.toJSONString(withdraw));
    }

    @Test
    public void testGetTemplate(){

        String accessToken = weChatProvider.getWeChatAccessToken();
        Result result = weChatProvider.sendTemplate("oXW4Mw2JMAlYYrH9R6X2VLbqFAGQ",new BigDecimal(100000));
        System.out.println(JSONObject.toJSONString(result));
    }
}



