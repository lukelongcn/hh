package com.h9.lottery.task;

import com.h9.common.db.entity.lottery.Reward;
import org.springframework.stereotype.Component;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.DateUtil;
import com.h9.lottery.config.LotteryConfig;
import com.h9.lottery.service.LotteryService;
import org.jboss.logging.Logger;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Calendar;
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
    

    @Scheduled(fixedRate = 5000)
    public void run() {

        List<Reward> rewardList = rewardRepository.findByEndTimeAndStatus(new Date());
        if(rewardList == null){
            return;
        }
        for (Reward reward : rewardList) {
            try {
                logger.info("rewardId: " + reward.getId() +""+  reward.getCode() + "开始");
                RLock lock = redisson.getLock("lock:" +  reward.getCode());
                try {
                    lock.lock(1000, TimeUnit.MILLISECONDS);
                    lotteryService.lottery(null, reward.getCode());
                } finally {
                    lock.unlock();
                }
                logger.info("rewardId: " + reward.getId() + "结束");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
