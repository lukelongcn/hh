package com.h9.lottery.interceptor;

import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.lottery.handle.UnAuthException;
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

/**
 * description: 登录权限认证拦截器
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private RedisBean redisBean;

    @SuppressWarnings("Duplicates")
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (o instanceof HandlerMethod) {
            //获限token
            String token = httpServletRequest.getHeader("token");
            HandlerMethod handlerMethod = (HandlerMethod) o;
            Secured secured = handlerMethod.getMethodAnnotation(Secured.class);
            if (secured != null) {
                if (StringUtils.isBlank(token)) throw new UnAuthException("未知用户");
                // token 失效检查
                String userId = redisBean.getStringValue(RedisKey.getTokenUserIdKey(token));
                if (StringUtils.isBlank(userId)) {
                    throw new UnAuthException("请重新登录");
                }
                MDC.put("userId",userId);
                httpServletRequest.getSession().removeAttribute("curUserId");
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
