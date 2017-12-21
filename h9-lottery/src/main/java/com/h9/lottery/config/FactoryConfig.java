package com.h9.lottery.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Created with IntelliJ IDEA.
 * FactoryConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/18
 * Time: 13:44
 */
@Configuration
@EnableAutoConfiguration
public class FactoryConfig {

    final static Logger logger= LoggerFactory.getLogger(FactoryConfig.class);

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

}