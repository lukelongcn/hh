package com.h9.admin.aop;

import com.h9.admin.handler.UnAuthException;
import com.h9.admin.interceptor.Secured;
import com.h9.common.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jboss.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * @author: George
 * @date: 2017/10/31 20:34
 */
@Aspect
@Component
@Order(2)
public class AuthAop {
    private Logger logger = Logger.getLogger(this.getClass());

    @Pointcut("execution(public * com.h9.admin.controller..*.*(..))")
    public void beforeAccess() {
    }

    @Before("execution(public * com.h9.admin.controller..*.*(..))&&@annotation(auth)")
    public void before(JoinPoint  joinPoint,Secured auth) throws Throwable {
        /*System.out.println("[Aspect1] before advise");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Class clazz = targetMethod.getClass();
        if (clazz.isAnnotationPresent(Secured.class)) {*/
            //获限token
            String token = HttpUtil.getHttpServletRequest().getHeader("token");
            if (StringUtils.isBlank(token)) throw new UnAuthException("未知用户");
            //String userId = redisBean.getStringValue(RedisKey.getAdminTokenUserIdKey(token));
            if (!token.equals(HttpUtil.getHttpSession().getAttribute("token"))) {
                throw new UnAuthException("请重新登录");
            }

       /* }*/
    }
}
