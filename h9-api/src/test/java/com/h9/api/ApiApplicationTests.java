package com.h9.api;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.interceptor.LoginAuthInterceptor;
import com.h9.api.model.dto.Areas;
import com.h9.api.provider.MobileRechargeService;
import com.h9.common.db.bean.JedisTool;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.user.UserBank;

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
import com.h9.common.db.entity.hotel.HotelRoomType;
import com.h9.common.db.entity.account.CardCoupons;
import com.h9.common.db.entity.order.China;
import com.h9.common.db.entity.order.GoodsType;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserExtends;
import com.h9.common.db.repo.*;

import com.h9.common.utils.DateUtil;
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
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {


    @Resource
    private JedisTool jedisTool;

    @Test
    public void contextLoads() {
        boolean b = jedisTool.tryGetDistributedLock("name", "ldh", 10);

        boolean b2 = jedisTool.tryGetDistributedLock("name", "ldh", 10);
        System.out.println(b+","+b2);
    }

}



