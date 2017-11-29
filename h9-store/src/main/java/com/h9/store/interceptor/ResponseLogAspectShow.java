package com.h9.store.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * description: 响应日志切面
 */
@Aspect
@Component
@Order
public class ResponseLogAspectShow {
    private Logger logger = Logger.getLogger(this.getClass());

    @Pointcut("execution(public * com.h9.store.controller..*.*(..))")
    public void pointCut() {}


    @AfterReturning(returning = "ret",pointcut = "pointCut()")
    public void after(Object ret) throws Throwable{

        logger.info("response content: "+ JSONObject.toJSONString(ret));
        logger.infov("---------------------------------------------");
        logger.info("");
    }
}
