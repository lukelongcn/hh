package com.h9.lottery.config;

import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;

import org.jboss.logging.Logger;
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

    // 获取最大扫码次数
    public int getDayMaxLotteryTime() {
        String dayMaxotteryCount = configService.getStringConfig("lottery:max:time");
        Integer time = -3600;
        try {
            time = Integer.valueOf(dayMaxotteryCount);
        } catch (NumberFormatException e) {
            logger.info("获取最大扫码次数失败！默认值：max: "+time);
        }
        return time;
    }

    // 获取最大扫码次数
    public int getDayMaxLotteryCount() {

        String dayMaxotteryCount = configService.getStringConfig("lottery:max:count");
        Integer max = 20;
        try {
            max = Integer.valueOf(dayMaxotteryCount);
        } catch (NumberFormatException e) {
            logger.info("获取最大扫码次数失败！默认值：max: "+max);
        }
        return max;
    }

    // 获取红包抽奖错误最大扫码次数
    public int getMaxLotteryErrorTime() {
        String time = configService.getStringConfig(ParamConstant.LOTTERY_ERROR_MAX_TIME);
        Integer maxTime = -3600;
        try {
            maxTime = Integer.valueOf(time);
        } catch (NumberFormatException e) {
            logger.info("获取最大扫码次数失败！默认值：max: "+maxTime);
        }
        return maxTime;
    }


    // 获取红包抽奖错误最大扫码次数
    public int getMaxLotteryErrorCount() {
        String dayMaxotteryCount = configService.getStringConfig("lottery:error:max:count");
        Integer max = 3;
        try {
            max = Integer.valueOf(dayMaxotteryCount);
        } catch (NumberFormatException e) {
            logger.info("获取最大扫码次数失败！默认值：max: "+max);
        }
        return max;
    }


    // 获取刷新间隔
    public int getLotteryRefresh() {
        String refreshStr = configService.getStringConfig(ParamConstant.REFRESH);
        Integer refresh = 0;
        try {
             refresh = Integer.valueOf(refreshStr);
        } catch (NumberFormatException e) {
            refresh = 10;
            logger.info("获取刷新间隔出错，默认值：" +refresh);
        }

        return refresh;
    }



    // 获取延后时间
    public int getDelay() {

        String delayStr = configService.getStringConfig(ParamConstant.DELAY);
        int delay = 0;
        try {
            delay = Integer.valueOf(delayStr);
        } catch (NumberFormatException e) {
            delay = 60;
            logger.info("获取延后时间参数失败，默认值："+delay);
        }

        return delay;
    }


    // 拿到查询错误规定间隔时间
    public int getIntervalTime(){
        int intervalTime = -60;
        try {
            String queryIntervalTime = configService.getStringConfig(ParamConstant.QUERY_INTERVAL_TIME);
            intervalTime = Integer.valueOf(queryIntervalTime);
        }catch (NumberFormatException e) {
            logger.info("获取查询错误规定间隔时间失败，默认值：" + intervalTime);
        }
        return intervalTime;
    }

    // 拿到间隔时间规定错误次数
    public int getErrorTimes(){

        int errorTime = 3;
        try {
            String errorTimes = configService.getStringConfig(ParamConstant.ERROR_TIMES);
            errorTime = Integer.valueOf(errorTimes);
        }catch (NumberFormatException e) {
            logger.info("获取间隔时间规定错误次数失败，默认值：" + errorTime);
        }
        return errorTime;
    }

}
