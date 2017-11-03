package com.h9.admin.interceptor;

import com.h9.admin.handler.UnAuthException;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * description: 登录权限认证拦截器
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Resource
    private RedisBean redisBean;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (o instanceof HandlerMethod) {
            //获限token
            String token = httpServletRequest.getHeader("token");
            Secured secured = ((HandlerMethod) o).getMethodAnnotation(Secured.class);
            if (secured != null) {
                if (StringUtils.isBlank(token)) throw new UnAuthException("未知用户");
                String userId = redisBean.getStringValue(RedisKey.getAdminTokenUserIdKey(token));
                if(StringUtils.isEmpty(userId)){
                    throw new UnAuthException("请登录");
                }
                redisBean.expire(RedisKey.getAdminTokenUserIdKey(token), 10, TimeUnit.MINUTES);
               // httpServletRequest.getSession().removeAttribute("curUserId");
                httpServletRequest.getSession().setAttribute("curUserId",userId);
            }

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
