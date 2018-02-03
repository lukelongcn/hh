package com.h9.lottery.task;

import com.h9.common.db.entity.lottery.Reward;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.DateUtil;
import com.h9.lottery.config.LotteryConfig;
import com.h9.lottery.service.LotteryService;
import org.jboss.logging.Logger;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * lotteryTask:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 16:28
 */
@Component
public class LotteryTask {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private LotteryService lotteryService;

    @Resource
    private RewardRepository rewardRepository;

    @Resource
    private LotteryConfig lotteryConfig;
    @Resource
    private Redisson redisson;
    

    @Scheduled(cron = "0/10 * * * * *")
    public void run() throws InterruptedException {

        long start = System.currentTimeMillis();
        List<Reward> rewardList = rewardRepository.findByEndTimeAndStatus(new Date());
        if(rewardList == null||rewardList.isEmpty()){
            return;
        }
        for (Reward reward : rewardList) {
            try {
                logger.info("rewardId: " + reward.getId() +""+  reward.getCode() + "开始");
                RLock lock = redisson.getLock("lock:start:" +  reward.getCode());
                try {
                    lock.lock(30, TimeUnit.SECONDS);
                    logger.debugv("lottery start 中奖名单为：定时任务开奖 code:{0}", reward.getCode());
                    lotteryService.lottery(null, reward.getCode());
                } finally {
                    lock.unlock();
                }
                logger.info("rewardId: " + reward.getId() + "结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        logger.debugv("定时开奖="+(end - start)+"毫秒"+rewardList.size());




    }
}
