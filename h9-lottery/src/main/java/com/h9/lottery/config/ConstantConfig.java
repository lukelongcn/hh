package com.h9.lottery.config;

import com.h9.common.db.entity.Lottery;
import org.jboss.logging.Logger;
import org.springframework.core.env.Environment;

/**
 * Created with IntelliJ IDEA.
 * Description:配置文件常量类
 * ConstantConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/18
 * Time: 10:40
 */
public class ConstantConfig {

    static Logger logger = Logger.getLogger(ConstantConfig.class);
    public static String Lottery_QR_PATH = "";
    public static String Lottery_QR_FORWARD_PATH = "";

    public static void init(Environment environment) {
        Lottery_QR_PATH = environment.getProperty("lottery.qr.path");
        Lottery_QR_FORWARD_PATH = environment.getProperty("lottery.qr.forward.path");
        logger.debugv("qr path {0}",Lottery_QR_PATH);
        logger.debugv("qr path {0}",Lottery_QR_FORWARD_PATH);
    }


    public static String path2Code(String code){
        if(code.contains("1h9.cc/")){
            return code.replace("1h9.cc/", "");
        } if(code.contains(Lottery_QR_PATH)){
            return code.replace(Lottery_QR_PATH, "");
        }
        return code;
    }



}
