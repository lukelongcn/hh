package com.h9.admin.job;

import com.h9.admin.service.ActivityService;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.OrdersLotteryActivityRep;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2018/1/18.
 */
@Component
public class StartBigRichLotteryJob {


    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private Lock lock;

    @Resource
    private ActivityService activityService;

        @Scheduled(cron = "0 0/5 * * * ?")
//    @Scheduled(fixedDelay = 100)
    public void scan() {

        logger.info("StartBigRichLotteryJob go ----------->");
        String lockKey = "h9:StartBigRichLotteryJob:lock";

        if (!lock.getLock(lockKey, 1, TimeUnit.MINUTES)) {
            return;
        }
        activityService.startBigRichLottery();
    }


}
