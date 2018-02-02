package com.h9.common.db.bean;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by itservice on 2018/2/2.
 */
@Component
public class SequenceUtil {
    @Resource
    private RedisTemplate redisTemplate;
    private RedisAtomicLong redisAtomicLong;

    private int initValue = 0;

    /**
     * 序列next value
     * @return
     */
    public long getNextVal(){
        if(redisAtomicLong==null){
            redisAtomicLong = new RedisAtomicLong("SceneId", redisTemplate.getConnectionFactory());
        }
        long value = redisAtomicLong.get();
        if(value < initValue){
            redisAtomicLong.set(initValue);
            return redisAtomicLong.get();
        }
        return redisAtomicLong.incrementAndGet();
    }

    /**
     * 序列当前值
     * @return
     */
    public long getVal(){
        if(redisAtomicLong==null){
            redisAtomicLong = new RedisAtomicLong("SceneId", redisTemplate.getConnectionFactory());

        }
        long value = redisAtomicLong.get();
        return value;
    }


}
