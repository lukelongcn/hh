package com.h9.api.config;

import org.jboss.logging.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Ln on 2018/4/16.
 */
@Component
public class SysConfig {

    @Resource
    private Environment environment;
    private Logger logger = Logger.getLogger(this.getClass());

    public Object getObj(String key) {
        return environment.getProperty(key);
    }

    public String getString(String key) {
        String value = environment.getProperty(key);
        if (value == null) {
            return "";
        }
        return  value;
    }

    public Boolean getBoolean(String key) {
        Object obj = getObj(key);

        try {
            Boolean result = (Boolean) obj;
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return null;
        }

    }
}
