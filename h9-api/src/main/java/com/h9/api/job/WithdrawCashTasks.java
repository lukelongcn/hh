package com.h9.api.job;

import com.h9.api.service.ConsumeService;
import com.h9.common.utils.DateUtil;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by itservice on 2017/11/7.
 */
@Component
public class WithdrawCashTasks {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private ConsumeService consumeService;
    //TODO 改时间改成 30分钟
    @Scheduled(cron = "0 0/5 * * * ?")
    public void run(){
        logger.debugv("提现定时检任务");
        consumeService.scan();
    }
}
