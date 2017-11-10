package com.h9.lottery.task;

import com.h9.lottery.service.LotteryService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * lotteryTask:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 16:28
 */
@Component
public class lotteryTask {
    @Resource
    private LotteryService lotteryService;




}
