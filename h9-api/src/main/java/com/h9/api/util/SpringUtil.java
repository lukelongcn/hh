package com.h9.api.util;

import org.jboss.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by itservice on 2017/10/28.
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /*
     * @param arg0
     *
     * @throws BeansException
     *
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public static <T> T getBean(String id, Class<T> type) {
        return applicationContext.getBean(id, type);
    }
}