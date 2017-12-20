package com.h9.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.annotations.PrintReqResLog;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 请求日志打印拦截器
 */
@SuppressWarnings("Duplicates")
@Component
public class RequestLogInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

    @Value("${h9.performance.consume.maxTime}")
    private int consumeMaxTime;

    @SuppressWarnings("Duplicates")
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //1、开始时间
        long beginTime = System.currentTimeMillis();
        //线程绑定变量（该数据只有当前请求的线程可见）
        startTimeThreadLocal.set(beginTime);

        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isNotEmpty(token)) MDC.put("token", token.substring(0, 8));
        String method = httpServletRequest.getMethod();
        if (HttpMethod.OPTIONS.name().equals(method)) {
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) o;
        PrintReqResLog printReqResLog = handlerMethod.getMethodAnnotation(PrintReqResLog.class);

        printReqInfo(httpServletRequest);

        if (printReqResLog == null) {
            //不加注解的方法，打请求参数日志
            printReqParams(httpServletRequest);
        } else {
            //加注解的方法，根据属性打请求参数日志
            if (printReqResLog.printRequestParams()) {
                printReqParams(httpServletRequest);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        long endTime = System.currentTimeMillis();//2、结束时间
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;//3、消耗的时间
        if (consumeTime > consumeMaxTime) {//此处认为处理时间超过500毫秒的请求为慢请求
            logger.info("lowPerformance: "+consumeTime+", url: " + httpServletRequest.getRequestURL());
        }


        HandlerMethod handlerMethod = (HandlerMethod) o;
        PrintReqResLog printReqResLog = handlerMethod.getMethodAnnotation(PrintReqResLog.class);
        printResInfo(httpServletResponse);

        if (printReqResLog == null) {
            printResResult(httpServletResponse);
        } else {
            if (printReqResLog.printResponseResult()) {
                printResResult(httpServletResponse);
            }
        }
    }



    /**
     * description: 打印请求参数
     */
    private void printReqInfo(HttpServletRequest httpServletRequest) {
        logger.info("");
        logger.infov("-------------------请求信息-------------------");
        logger.info("method: " + httpServletRequest.getMethod());
        logger.info("url: " + httpServletRequest.getRequestURL());
        logger.info("content-type: " + httpServletRequest.getHeader("Content-Type"));

    }

    /**
     * description: 打印请求信息
     */
    private void printReqParams(HttpServletRequest httpServletRequest) throws IOException {

        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        String paramStr = JSONObject.toJSONString(parameterMap);

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            headers.put(key, value);
        }


        int contentLength = httpServletRequest.getContentLength();
        if (contentLength != -1) {

            byte buffer[] = new byte[contentLength];
            for (int i = 0; i < contentLength; ) {

                int readlen = httpServletRequest.getInputStream().read(buffer, i,
                        contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }
            logger.info("request param: " + new String(buffer));
        } else {
            logger.info("request param: " + new String(paramStr));
        }

        logger.info("request headers : " + JSONObject.toJSONString(headers));
        logger.info("---------------------------------------------");
    }

    /**
     * description: 打印响应参数
     */
    private void printResInfo(HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("-------------------响应信息-------------------");
        logger.info("http code : " + response.getStatus());

    }

    /**
     * description: 打印响应参数
     */
    private void printResResult(HttpServletResponse response) throws UnsupportedEncodingException {
        CustomServletResponseWrapper customServletResponseWrapper = (CustomServletResponseWrapper) response;
        String responseStr = new String(customServletResponseWrapper.toByteArray(), response.getCharacterEncoding());
        logger.info("response content: " + responseStr);
        logger.info("---------------------------------------------");
        logger.info("");

    }

}
