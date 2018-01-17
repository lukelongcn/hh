package com.h9.api.interceptor;

import com.h9.api.model.dto.Areas;
import com.h9.api.model.dto.PayConfig;
import com.h9.api.service.AddressService;
import com.h9.api.service.UserService;
import com.h9.api.service.handler.PayHandler;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import org.jboss.logging.Logger;
import com.h9.common.db.repo.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        this.initAddressCache();
        payHandler.initPay();
    }


    /****
     * 初始化地址区域信息
     */
    private void initAddressCache() {

        logger.info("初始化数据中0.0");
        long start = System.currentTimeMillis();
        addressService.allArea();
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
