package com.h9.api.job;

import com.h9.api.service.ConsumeService;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/7.
 */
@Component
public class WithdrawCashTasks {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ConsumeService consumeService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run(){

        logger.info("withdrawCashTasks run ........");
        consumeService.scan();
    }
}
