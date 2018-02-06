package com.h9.lottery.task;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.DateUtil;
import com.h9.lottery.config.LotteryConfig;
import com.h9.lottery.service.LotteryService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.stream.Collectors;

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
    

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() throws InterruptedException {
        boolean lock4Task = getLock();
        if (!lock4Task) {
            return;
        }
        long start = System.currentTimeMillis();
        List<Reward> rewardList = rewardRepository.findByEndTimeAndStatus(new Date());
        if(rewardList == null||rewardList.isEmpty()){
            return;
        }
        List<String> collect = rewardList.stream().map(reward -> reward.getCode()).collect(Collectors.toList());
        logger.debugv("lottery start"+ JSONObject.toJSONString(collect));
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

    @Resource
    private RedisBean redisBean;
    

    /**
     * description: 获取redis 定时任务锁
     */
    public boolean getLock(){

        String formatDate = DateUtil.formatDate(new Date(), DateUtil.FormatType.NON_SEPARATOR_MINUTE);

        String lockKey = "h9:lottery:lock:"+formatDate;

        String value = redisBean.getStringValue(lockKey);

        if (StringUtils.isBlank(value)) {
            redisBean.setStringValue(lockKey,"LOCK",5, TimeUnit.MINUTES);
            logger.info("获取定时任务锁成功");
            return true;
        }
        logger.info("获取定时任务锁失败");
        return false;
    }

}
