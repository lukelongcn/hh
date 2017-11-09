package com.h9.admin.interceptor;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * 处理page  让页面从1开始
 * Created by Gonyb on 2017/8/25.
 */
@Aspect
@Component
@Order(5)//定义优先顺序 越小优先级越高
public class HandlerPageInterceptor {
    /**
     * 定义拦截规则：拦截com.h9.admin.controller包下面的所有类中的方法
     */
    @Pointcut("execution(* com.h9.admin.controller..*(..)) ")
    public void controllerMethodPointcut(){
        //这个空方法用来做切入点 
    }

    @Around("controllerMethodPointcut()")
    Object doAround(ProceedingJoinPoint joinPoint) throws Throwable  {

        Object proceed = joinPoint.proceed();
        
        if(proceed instanceof Result){
            Result doResult = (Result) proceed;
            if(doResult.getData() instanceof Page){
                Page page = (Page) doResult.getData();
                PageResult pageResult = new PageResult(page);
                doResult.setData(pageResult);
                return doResult;
            }
        }
        return proceed;
    }
}
