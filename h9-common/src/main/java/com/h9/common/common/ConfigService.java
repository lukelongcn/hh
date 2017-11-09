package com.h9.common.common;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.repo.GlobalPropertyRepository;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * ConfigService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/9
 * Time: 11:00
 */
public class ConfigService {

    @Resource
    private RedisBean redisBean;

    @Resource
    private GlobalPropertyRepository globalPropertyRepository;



    public Object getConfig(String code){
        if(code==null) {
            return null;
        }
        Object valueRedis1 = getConfigFromCache(code);
        if (valueRedis1 != null) return valueRedis1;
        return getConfigFromDb(code);
    }

    public String getStringConfig(String code){
        Object config = getConfig(code);
        if(config instanceof String){
            return (String) config;
        }else{
            return null;
        }
    }

    public List<String> getStringListConfig(String code){
        Object config = getConfig(code);
        if(config instanceof List){
            return (List<String>) config;
        }else{
            return null;
        }
    }


    public Map getMapConfig(String code){
        Object config = getConfig(code);
        if(config instanceof Map){
            return (Map) config;
        }else{
            return null;
        }
    }

    public List<Map<String,String>> getMapListConfig(String code){
        Map mapConfig = getMapConfig(code);
        if(mapConfig == null){
            return null;
        }
        return null;
    }



    private Object getConfigFromDb(String code) {
        GlobalProperty globalProperty = globalPropertyRepository.findByCode(code);
        if (globalProperty != null) {
            String val = globalProperty.getVal();
            String type = globalProperty.getType() + "";
            redisBean.setStringValue(RedisKey.getConfigValue(code),val);
            redisBean.setStringValue(RedisKey.getConfigType(code),type);
            return getValue(type, val);
        }else{
            return null;
        }
    }

    private Object getConfigFromCache(String code) {
        String typeRedis = redisBean.getStringValue(RedisKey.getConfigType(code));
        String valueRedis = redisBean.getStringValue(RedisKey.getConfigValue(code));
        if(StringUtils.isNotEmpty(valueRedis)&&StringUtils.isNotEmpty(typeRedis)){
            Object map = getValue(typeRedis, valueRedis);
            if (map != null) return map;
        }
        return null;
    }

    private Object getValue(String typeRedis, String valueRedis) {
        if(typeRedis.equals("0")){
            return valueRedis;
        }
        if(typeRedis.equals("1")){
            Map map = JSONObject.parseObject(valueRedis, Map.class);
            return map;
        }
        if(typeRedis.equals("2")){
            List<String> strings = JSONObject.parseArray(valueRedis, String.class);
            return strings;
        }
        return null;
    }

}
