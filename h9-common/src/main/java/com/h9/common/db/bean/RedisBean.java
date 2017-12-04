package com.h9.common.db.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SupplierGoods:刘敏华 shadow.liu@hey900.com
 * Date: 2017-08-09
 * Time: 14:35
 */
@Component
public class RedisBean {
    private static final Logger logger = Logger.getLogger(RedisBean.class);

    @Autowired
    private StringRedisTemplate stringTemplate;

    @Resource(name = "stringRedisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    @Resource(name = "stringRedisTemplate")
    private HashOperations<String, Object, Object> hashOps;

    @Resource(name = "stringRedisTemplate")
    private SetOperations<String, String> setOps;

    @Resource(name = "stringRedisTemplate")
    private ZSetOperations<String, String> zsetOps;

    private List list;

    public void setObject(String key,Object object){
        String value = JSONObject.toJSONString(object);
        logger.infov("redis: setStringValue({0},{1})", key,value );
        valueOps.set(key, value);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        try {
            String value = valueOps.get(key);
            logger.infov("redis: getStringValue({0}) = {1}", key, value);

            if (StringUtils.isEmpty(value)) {
                logger.warnv("redis: getStringValue({0}) = {1}", key, value);
                return null;
            }
            return JSONObject.parseObject(value,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public <T> List<T> getArray(String key, Class<T> clazz){
        List list = new ArrayList();
        try {
            String value = valueOps.get(key);
            logger.infov("redis: getStringValue({0}) = {1}", key, value);

            if (StringUtils.isEmpty(value)) {
                logger.warnv("redis: getStringValue({0}) = {1}", key, value);
                return null;
            }
            return JSONArray.parseArray(value,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ValueOperations<String, String> getValueOps() {
        return valueOps;
    }

    public StringRedisTemplate getStringTemplate() {
        return stringTemplate;
    }

    public void setStringTemplate(StringRedisTemplate stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    public ListOperations<String, String> getListOps() {
        return listOps;
    }

    public SetOperations<String, String> getSetOps() {
        return setOps;
    }

    public ZSetOperations<String, String> getZSetOps() {
        return zsetOps;
    }

    public Object getHashValue(String key, String hashKey) {
        Object value = hashOps.get(key, hashKey);
        logger.infov("redis: getHashValue({0},{1}) = {2}", key, hashKey, value);
        if (value == null) {
            logger.warnv("redis: getHashValue({0},{1}) = {2}", key, hashKey,
                    value);
        }
        return value;
    }

    public void delHashValue(String key, String hashKey) {
        hashOps.delete(key, hashKey);
    }

    public String getHashValueNoWarn(String key, String hashKey) {
        Object value = hashOps.get(key, hashKey);
        logger.infov("redis: getHashValue({0},{1}) = {2}", key, hashKey, value);
        return (String) value;
    }

    public String getHashStringValue(String key, String hashKey) {
        return (String) getHashValue(key, hashKey);
    }

    public void setHashStringValue(String key, String hashKey, String value) {
        hashOps.put(key, hashKey, value);
    }

    public void putHashValue(String key, String hashKey, Object value) {
        logger.infov("redis: putHashValue({0},{1},{2})", key, hashKey, value);

        hashOps.put(key, hashKey, value);
    }

    public String getStringValue(String key) {
        String value = valueOps.get(key);
        logger.infov("redis: getHashValue({0}) = {1}", key, value);

        if (value == null) {
            logger.warnv("redis: getStringValue({0}) = {1}", key, value);
        }
        return value;
    }

    public String getStringValueNoWarn(String key) {
        String value = valueOps.get(key);
        logger.infov("redis: getStringValueNoWarn({0}) = {1}", key, value);
        return value;
    }

    public void setStringValue(String key, String value, int timeout,
                               TimeUnit unit) {
        logger.infov("redis: setStringValue({0},{1},{2},{3})", key, value,
                timeout, unit);
        valueOps.set(key, value, timeout, unit);
    }

    public void setStringValue(String key, String value) {
        logger.infov("redis: setStringValue({0},{1})", key, value);
        valueOps.set(key, value);
    }

    public Set<String> members(String key) {
        Set<String> value = setOps.members(key);
        logger.infov("redis: members({0}) = {1}", key, value);
        if (value == null) {
            logger.warnv("redis: members({0}) = {1}", key, value);
        }
        return value;
    }

    public Boolean isMember(String key, Object o) {
        logger.infov("redis: isMember({0}) = {1}", key, o);
        return setOps.isMember(key, o);
    }

    public List<String> range(String key, long start, long end) {
        List<String> value = listOps.range(key, start, end);
        logger.infov("redis: range({0},{1},{2}) = {3}", key, start, end, value);
        if (value == null || value.isEmpty()) {
            logger.warnv("redis: range({0},{1},{2}) = {3}", key, start, end,
                    value);
        }
        return value;
    }

    public Set<String> zrange(String key, long start, long end) {
        Set<String> value = zsetOps.range(key, start, end);
        logger.infov("redis: zrange({0},{1},{2}) = {3}", key, start, end, value);
        if (value == null || value.isEmpty()) {
            logger.warnv("redis: zrange({0},{1},{2}) = {3}", key, start, end,
                    value);
        }
        return value;
    }

    public long zsize(String key) {
        return zsetOps.size(key);
    }

    public long size(String key) {
        return listOps.size(key);
    }

    public boolean expire(String key, Date date) {
        stringTemplate.expireAt(key, date);
        return true;
    }

    public boolean expire(String key, final long timeout, final TimeUnit unit) {
        stringTemplate.expire(key, timeout, unit);
        return true;
    }

    public long lpush(String k, Collection<String> vs) {
        logger.infov("redis:lpush {0}={1}", k, vs);
        if (vs == null || vs.isEmpty()) {
            logger.warnv("redis:lpush is null add {0} = {1}", k, vs);
        }
        return listOps.leftPushAll(k, vs);
    }



}
