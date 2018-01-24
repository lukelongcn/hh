package com.h9.admin.job;

import com.h9.common.db.bean.RedisBean;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2018/1/24.
 */
@Component
public class Lock {

    @Resource
    private RedisBean redisBean;
    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * description: 获取redis 定时任务锁
     */
    public boolean getLock(String lockKey){



        String value = redisBean.getStringValue(lockKey);

        if (StringUtils.isBlank(value)) {
            redisBean.setStringValue(lockKey,"LOCK",5, TimeUnit.SECONDS);
            logger.info("获取定时任务锁成功, key: "+lockKey);
            return true;
        }
        logger.info("获取定时任务锁失败,key: "+lockKey);
        return false;
    }
}
