package com.h9.lottery.config;

import com.h9.common.common.ConfigService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/10.
 */
@Component
public class LotteryConfig {
    @Resource
    private ConfigService configService;

    private Logger logger = Logger.getLogger(this.getClass());
    public int getDayMaxotteryCount() {

        String dayMaxotteryCount = configService.getStringConfig("dayMaxlotteryCount");
        Integer max = null;
        try {
            max = Integer.valueOf(dayMaxotteryCount);
        } catch (NumberFormatException e) {
            max = 10;
            logger.info("获取最在扫码次数失败！默认值：max: "+max);
        }
        return max;
    }



    public int getLotteryRefresh() {
        String refreshStr = configService.getStringConfig("refresh");
        Integer refresh = 0;
        try {
             refresh = Integer.valueOf(refreshStr);
        } catch (NumberFormatException e) {
            refresh = 10;
            logger.info("获取最刷新间隔出错，默认值：" +refresh);
        }

        return refresh;
    }



    public int getDelay() {

        String delayStr = configService.getStringConfig("delay");
        int delay = 0;
        try {
            delay = Integer.valueOf(delayStr);
        } catch (NumberFormatException e) {
            delay = 60;
            logger.info("获取延后时间参数失败，默认值："+delay);
        }

        return delay;
    }


}
