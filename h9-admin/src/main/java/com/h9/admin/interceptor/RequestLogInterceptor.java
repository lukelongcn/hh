package com.h9.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.annotations.PrintReqResLog;
import org.jboss.logging.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * description: 请求日志打印拦截器
 */
@SuppressWarnings("Duplicates")
@Component
public class RequestLogInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //1、开始时间
        long beginTime = System.currentTimeMillis();
        //线程绑定变量（该数据只有当前请求的线程可见）
        startTimeThreadLocal.set(beginTime);

        String method = httpServletRequest.getMethod();
        if (HttpMethod.OPTIONS.name().equals(method)) {
            return false;
        }
        logger.infov("-------------------请求信息-------------------");
        logger.info("method: " + httpServletRequest.getMethod());
        logger.info("url: " + httpServletRequest.getRequestURL());
        logger.info("content-type: " + httpServletRequest.getHeader("Content-Type"));
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
            HandlerMethod handlerMethod = (HandlerMethod) o;
            // 从方法处理器中获取出要调用的方法
            Method m = handlerMethod.getMethod();
            this.printParam(m,buffer);
        }else{
            logger.info("request param: " + new java.lang.String(paramStr));
        }

        logger.info("request headers : " + JSONObject.toJSONString(headers));
//        logger.infov("---------------------------------------------");
        logger.info("");
        logger.infov("-------------------响应信息-------------------");
        logger.info("http code : " + httpServletResponse.getStatus());
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
        if (consumeTime > 300) {//此处认为处理时间超过300毫秒的请求为慢请求
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

    //打印参数
    private void printParam(Method m,byte[] buffer){
        // 获取方法上的PrintParam注解
        PrintParam printParam = m.getAnnotation(PrintParam.class);
        if(printParam !=null){
            if(printParam.printType()==PrintType.NOT_PRINT){
                Map map = JSONObject.parseObject(new String(buffer),Map.class);
                if(printParam.notPrint().length>0){
                    for(String key: printParam.notPrint()){
                        map.remove(key);
                    }
                    /*Iterator<String> notPrintIterator = printParam.notPrint();
                    while () {

                    }*/
                }
                logger.info("request param: " + JSONObject.toJSONString(map));
            }else if(printParam.printType()==PrintType.PRINT){
                if(printParam.print().length>0){
                    Map map = JSONObject.parseObject(new String(buffer),Map.class);
                    Map printMap = new HashMap();
                    for(String key: printParam.print()){
                        printMap.put(key,map.get(key));
                    }
                    logger.info("request param: " + JSONObject.toJSONString(printMap));
                }
            }
        }else{
            logger.info("request param: " + new java.lang.String(buffer));
        }
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
