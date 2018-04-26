package com.h9.common.db.bean;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Created by Ln on 2018/4/20.
 */
@Component
public class JedisTool {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private JedisPool jedisPool;

    public boolean tryGetDistributedLock(String lockKey, String requestId, long expireTime) {

        Jedis jedis = jedisPool.getResource();
        String result = null;
        try {

            result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
//            result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return false;
        } finally {
            jedis.close();
        }

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }


    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        Jedis jedis = jedisPool.getResource();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = null;
        try {
            result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return false;
        } finally {
            jedis.close();
        }

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

}
