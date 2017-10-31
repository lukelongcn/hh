package com.h9.admin.aop;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.utils.HttpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: George
 * @date: 2017/10/27 18:29
 */
@Aspect
@Component
@Order(1)
public class LogAop {
    private Logger logger = Logger.getLogger(this.getClass());

    @Around("execution(public * com.h9.admin.controller..*.*(..))")
    public Object httpLog(ProceedingJoinPoint pjp) throws Throwable{
        printRequest();
        Object result = pjp.proceed();// result的值就是被拦截方法的返回值
        printResponse(result);
        return result;
    }

    private void printRequest(){
        HttpServletRequest httpServletRequest = HttpUtil.getHttpServletRequest();
        logger.infov("-------------------请求信息-------------------");
        logger.info("method: " + httpServletRequest.getMethod());
        logger.info("url: " + httpServletRequest.getRequestURL());
        logger.info("content-type: " + httpServletRequest.getHeader("Content-Type"));
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        String paramStr = JSONObject.toJSONString(parameterMap);
        logger.info("request param: " + paramStr);
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            headers.put(key, value);
        }
        logger.info("request headers : " + JSONObject.toJSONString(headers));
        logger.info("");
    }

    private void printResponse(Object result){
        HttpServletResponse httpServletResponse = HttpUtil.getHttpServletResponse();
        logger.infov("-------------------响应信息-------------------");
        logger.info("http code : " + httpServletResponse.getStatus());
        logger.info("response content: "+ JSONObject.toJSONString(result));
        logger.info("");
    }
}
