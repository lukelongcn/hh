package com.h9.api.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by itservice on 2017/10/26.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册 登录权限校验拦截器
        registry.addInterceptor(new LoginAuthInterceptor()).addPathPatterns("/**");
        //注册 日志打印拦截器
        registry.addInterceptor(new RequestLogInterceptor()).addPathPatterns("/**");

    }
}
