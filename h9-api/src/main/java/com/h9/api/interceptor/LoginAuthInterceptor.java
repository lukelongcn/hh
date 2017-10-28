package com.h9.api.interceptor;

import com.h9.api.handle.UnAuthException;
import com.h9.api.util.SpringUtil;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by itservice on 2017/10/26.
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (o instanceof HandlerMethod) {
            //获限token
            String token = httpServletRequest.getHeader("token");
            Secured secured = ((HandlerMethod) o).getMethodAnnotation(Secured.class);

            if (secured != null) {
                if (StringUtils.isBlank(token)) throw new UnAuthException("请重新登录");
                // token 失效检查
                RedisBean redisBean = SpringUtil.getBean("redisBean", RedisBean.class);
                String userId = redisBean.getStringValue(RedisKey.getTokenUserIdKey(token));
                if (StringUtils.isBlank(userId)) {
                    throw new UnAuthException("请重新登录");
                }
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
