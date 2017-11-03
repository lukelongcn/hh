package com.h9.admin.interceptor;

import com.h9.common.db.repo.BannerTypeRepository;

import org.jboss.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:权限初始化监听器
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017-08-09
 * Time: 15:47
 */
@Component
public class InitListener implements ApplicationListener<ApplicationReadyEvent> {
    private Logger logger = Logger.getLogger(InitListener.class);
    @Resource
    private BannerTypeRepository bannerTypeRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        TODO 初始化数据

    }

}
