package com.h9.lottery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by itservice on 2017/11/10.
 */
@Component
public class LotteryConfig {
    @Value("${h9.lottery.dayMaxotteryCount}")
    public int dayMaxotteryCount;
    @Value("${h9.lottery.refresh}")
    private int lotteryRefresh;
    @Value("${h9.lottery.delay}")
    private int delay;

    public int getDayMaxotteryCount() {
        return dayMaxotteryCount;
    }

    public void setDayMaxotteryCount(int dayMaxotteryCount) {
        this.dayMaxotteryCount = dayMaxotteryCount;
    }

    public int getLotteryRefresh() {
        return lotteryRefresh;
    }

    public void setLotteryRefresh(int lotteryRefresh) {
        this.lotteryRefresh = lotteryRefresh;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
