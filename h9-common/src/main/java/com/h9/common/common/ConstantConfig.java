package com.h9.common.common;

import com.h9.common.constant.ParamConstant;
import org.jboss.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.applet.AppletContext;

/**
 * Created with IntelliJ IDEA.
 * Description:配置文件常量类
 * ConstantConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/18
 * Time: 10:40
 */
public class ConstantConfig {

    static Logger logger = Logger.getLogger(ConstantConfig.class);
    public static String DEFAULT_HEAD = "";


    public static void init(ApplicationContext applicationContext,Environment environment) {
        ConfigService configService = applicationContext.getBean(ConfigService.class);
        DEFAULT_HEAD =  configService.getStringConfig(ParamConstant.DEFUALT_HEAD);
    }




}
