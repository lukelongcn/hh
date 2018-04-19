package com.h9.common.common;

import com.h9.common.db.bean.RedisBean;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2018/1/24.
 */
@Component
public class MyLock {

    @Resource
    private RedisBean redisBean;
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * description: 获取redis 定时任务锁
     */
    public boolean getLockAndRelease(String lockKey) {

        String value = redisBean.getStringValue(lockKey);

        if (StringUtils.isBlank(value)) {
            redisBean.setStringValue(lockKey, "LOCK", 5, TimeUnit.MINUTES);
            logger.info("获取定时任务锁成功, key: " + lockKey);
            return true;
        }
        logger.info("获取定时任务锁失败,key: " + lockKey);
        return false;
    }

    /**
     * 用指定 key 获取锁，若获取到了锁，锁会指定时间内释放掉
     *
     * @param lockKey
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public boolean getLockAndRelease(String lockKey, int timeOut, TimeUnit timeUnit) {

        String value = redisBean.getStringValue(lockKey);

        if (StringUtils.isBlank(value)) {
            redisBean.setStringValue(lockKey, "LOCK", timeOut, timeUnit);
            logger.info("获取定时任务锁成功, key: " + lockKey);
            return true;
        }
        logger.info("获取定时任务锁失败,key: " + lockKey);
        return false;
    }

    public void lock(String key) {
        redisBean.setStringValue(key, "lock");
    }

    public void lock(String key, int nTime, TimeUnit timeUnit) {
        redisBean.setStringValue(key, "lock", nTime, timeUnit);
    }


    public void lock(String key, String value) {
        redisBean.setStringValue(key, value);
    }

    public void lock(String key, String value, int nTime, TimeUnit timeUnit) {
        redisBean.setStringValue(key, value, nTime, timeUnit);
    }

    public void unLock(String key) {
        redisBean.setStringValue(key, "", 1, TimeUnit.MILLISECONDS);
    }

    public String getLock(String key) {
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        String stringValue = redisBean.getStringValue(key);
        return stringValue;
    }
}
