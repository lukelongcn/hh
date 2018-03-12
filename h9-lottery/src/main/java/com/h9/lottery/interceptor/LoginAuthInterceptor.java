package com.h9.lottery.interceptor;

import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.utils.DateUtil;
import com.h9.lottery.handle.UnAuthException;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
                if (StringUtils.isBlank(token)) throw new UnAuthException(401,"未知用户");
                // token 失效检查
                String userId4phone = redisBean.getStringValue(RedisKey.getTokenUserIdKey(token));
                String userId4WeChat = redisBean.getStringValue(RedisKey.getWeChatUserId(token));
                if(StringUtils.isEmpty(userId4WeChat)&&StringUtils.isEmpty(userId4phone)){
                    throw new UnAuthException(401,"请重新登录");
                }
                String userId = "";
                if(!StringUtils.isEmpty(userId4WeChat)){
                    if(secured.bindPhone()){
                        throw new UnAuthException(402,"绑定手机号");
                    }
                    userId = userId4WeChat;
                }
                if(!StringUtils.isEmpty(userId4phone)){
                    userId = userId4phone;
                }
                if(StringUtils.isEmpty(userId)){
                    throw new UnAuthException(401,"请重新登录");
                }
                MDC.put("userId",userId);

                addUserCount(userId);

                httpServletRequest.getSession().removeAttribute("curUserId");
                httpServletRequest.getSession().setAttribute("curUserId",userId);
            }
        }
        return true;
    }

    public void addUserCount(String userId){
        try {
            Long userIdLong = Long.valueOf(userId);
            logger.info("userIdLong : "+userIdLong);


            String day = DateUtil.formatDate(new Date(), DateUtil.FormatType.DAY);
            redisBean.getValueOps().setBit("h9:user:count:"+day,userIdLong,true);
            redisBean.getValueOps().setBit(RedisKey.getUserCountKey(new Date()), userIdLong, true);
            Long userCount = redisBean.getStringTemplate()
                    .execute((RedisCallback<Long>) connection ->
                            connection.bitCount(((RedisSerializer<String>) redisBean.getStringTemplate()
                                    .getKeySerializer())
                                    .serialize(DateUtil.formatDate(new Date(), DateUtil.FormatType.DAY))));
            logger.info("userCount: "+userCount);
        } catch (NumberFormatException e) {
            logger.info("解析UserId 出错: " + userId);
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
