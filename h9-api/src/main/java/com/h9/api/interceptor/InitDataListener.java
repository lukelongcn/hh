package com.h9.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.Areas;
import com.h9.api.model.dto.MenuDTO;
import com.h9.api.model.dto.PayConfig;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.AddressService;
import com.h9.api.service.UserService;
import com.h9.api.service.handler.PayHandler;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import org.jboss.logging.Logger;
import com.h9.common.db.repo.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by 李圆 on 2017/12/4
 */
@Component
public class InitDataListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    AddressService addressService;

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserService userService;
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private PayHandler payHandler;
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private WeChatProvider weChatProvider;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        this.initAddressCache();
        payHandler.initPay();
//        this.initWXMenu();
        //TODO 以后加回来
    }

    /**
     * 初始化微信公众号的菜单
     *
     */
    private void initWXMenu() {
        weChatProvider.createMenu();
    }

    /****
     * 初始化地址区域信息
     */
    private void initAddressCache() {
logger.info("初始化数据中0.0");
        long start = System.currentTimeMillis();       addressService.allArea();
        long end = System.currentTimeMillis();
        float initDateTime = (end - start) / 1000F;
        logger.info("初始化数据完成，消费时间: "+initDateTime +" 秒");
    }

    /*****
     * 初始化用户信息
     */
    private void initTestUserService() {
//   12345678909

    }

}
