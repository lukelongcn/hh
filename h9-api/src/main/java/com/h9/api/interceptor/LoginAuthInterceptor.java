package com.h9.api.interceptor;

import com.h9.api.handle.UnAuthException;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
                if (StringUtils.isBlank(token)) throw new UnAuthException(401, "请先登录");
                // token 失效检查
                String userId4phone = redisBean.getStringValue(RedisKey.getTokenUserIdKey(token));
                if (StringUtils.isBlank(token)) throw new UnAuthException(401, "登录超时，请重新登录");
                String weChatUserIdKey = RedisKey.getWeChatUserId(token);
                logger.info("weChatUserIdKey: "+weChatUserIdKey);
                String userId4WeChat = redisBean.getStringValue(weChatUserIdKey);
                if (StringUtils.isEmpty(userId4WeChat) && StringUtils.isEmpty(userId4phone)) {
                    throw new UnAuthException(401, "请重新登录");
                }
                String userId = "";
                if (!StringUtils.isEmpty(userId4phone)) {
                    userId = userId4phone;
                }
                if (!StringUtils.isEmpty(userId4WeChat)) {
                    logger.info("userId4WeChat: " + userId4WeChat);
                    logger.info("" + secured.bindPhone());
                    if (secured.bindPhone()) {
                        throw new UnAuthException(402, "绑定手机号");
                    }
                    userId = userId4WeChat;
                }
                MDC.put("userId", userId);

                if (StringUtils.isEmpty(userId)) {
                    throw new UnAuthException(401, "请重新登录");
                }

                MDC.put("userId", userId);
                httpServletRequest.getSession().removeAttribute("curUserId");
                httpServletRequest.getSession().setAttribute("curUserId", userId);
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
