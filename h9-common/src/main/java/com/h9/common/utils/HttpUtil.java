package com.h9.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: George
 * @date: 2017/10/31 17:51
 */
public class HttpUtil {

    public static HttpServletRequest getHttpServletRequest(){
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getHttpServletResponse(){
        return getServletRequestAttributes().getResponse();
    }

    public static HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();
    }

    public static void setHttpSessionAttr(String key,Object val){
        getHttpSession().setAttribute(key,val);
    }

    public static Object getHttpSessionAttr(String key){
        return getHttpSession().getAttribute(key);
    }

    public static ServletRequestAttributes getServletRequestAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static Long getCurrentUserId(){
        return Long.valueOf(getHttpSessionAttr("curUserId").toString());
    }

}
