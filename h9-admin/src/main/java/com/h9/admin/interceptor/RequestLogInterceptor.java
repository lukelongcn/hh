package com.h9.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.jboss.logging.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 请求日志打印拦截器
 */
@Component
public class RequestLogInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {


        logger.infov("-------------------请求信息-------------------");
        logger.info("method: " + httpServletRequest.getMethod());
        logger.info("url: " + httpServletRequest.getRequestURL());
        logger.info("content-type: " + httpServletRequest.getHeader("Content-Type"));
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        String paramStr = JSONObject.toJSONString(parameterMap);
        //if("login".equals(httpServletRequest.getMethod().))
        if (o instanceof HandlerMethod){
            NotPrintParam notPrintParam = ((HandlerMethod) o).getMethodAnnotation(NotPrintParam.class);
            if(notPrintParam==null){
                logger.info("request param: " + paramStr);
            }
        }else{
            logger.info("request param: " + paramStr);
        }
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            headers.put(key, value);
        }
        logger.info("request headers : " + JSONObject.toJSONString(headers));
//        logger.infov("---------------------------------------------");
        logger.info("");
        logger.infov("-------------------响应信息-------------------");
        logger.info("http code : " + httpServletResponse.getStatus());
        String method = httpServletRequest.getMethod();
        if(HttpMethod.OPTIONS.name().equals(method)){
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
