package com.h9.common.common;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.repo.GlobalPropertyRepository;
import com.h9.common.modle.vo.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:获取全局通用配置项
 * ConfigService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/9
 * Time: 11:00
 */
@Component
public class ConfigService {

    @Resource
    private RedisBean redisBean;


    @Resource
    private GlobalPropertyRepository globalPropertyRepository;


    public Object getConfig(String code) {
        if (code == null) {
            return null;
        }
        Object valueRedis1 = getConfigFromCache(code);
        if (valueRedis1 != null) return valueRedis1;
        return getConfigFromDb(code);
    }

    public void expireConfig(String code) {
       this.redisBean.expire(RedisKey.getConfigValue(code),1, TimeUnit.MILLISECONDS);
    }

    public String getStringConfig(String code) {
        Object config = getConfig(code);
        if (config instanceof String) {
            return (String) config;
        } else {
            return null;
        }
    }

    public List<String> getStringListConfig(String code) {
        Object config = getConfig(code);
        if (config instanceof List) {
            return (List<String>) config;
        } else {
            return null;
        }
    }


    public Map getMapConfig(String code) {
        Object config = getConfig(code);
        if (config instanceof Map) {
            return (Map) config;
        } else {
            return null;
        }
    }

    public List<Config> getMapListConfig(String code) {
        Map mapConfig = getMapConfig(code);
        if (mapConfig == null) {
            return null;
        }
        List<Config> configs = new ArrayList<>();
        Set<String> set = mapConfig.keySet();
        for(String key:set){
            configs.add(new Config(key, (String)mapConfig.get(key)));
        }
        return configs;
    }

    public  String getValueFromMap(String code,String key){
        Map<String,String> mapConfig = getMapConfig(code);

        if(mapConfig != null){
            return mapConfig.get(key);
        }

        return "";
    }

    private Object getConfigFromDb(String code) {
        GlobalProperty globalProperty = globalPropertyRepository.findByCode(code);
        if (globalProperty != null) {
            String val = globalProperty.getVal();
            String type = globalProperty.getType() + "";
            redisBean.setStringValue(RedisKey.getConfigValue(code), val);
            redisBean.setStringValue(RedisKey.getConfigType(code), type);
            return getValue(type, val);
        } else {
            return null;
        }
    }

    private Object getConfigFromCache(String code) {
        String typeRedis = redisBean.getStringValue(RedisKey.getConfigType(code));
        String valueRedis = redisBean.getStringValue(RedisKey.getConfigValue(code));
        if (StringUtils.isNotEmpty(valueRedis) && StringUtils.isNotEmpty(typeRedis)) {
            Object map = getValue(typeRedis, valueRedis);
            if (map != null) return map;
        }
        return null;
    }

    private Object getValue(String typeRedis, String valueRedis) {
        if (typeRedis.equals("0")) {
            return valueRedis;
        }
        if (typeRedis.equals("1")) {
            Map map = JSONObject.parseObject(valueRedis, Map.class);
            return map;
        }
        if (typeRedis.equals("2")) {
            List<String> strings = JSONObject.parseArray(valueRedis, String.class);
            return strings;
        }
        return null;
    }


}

