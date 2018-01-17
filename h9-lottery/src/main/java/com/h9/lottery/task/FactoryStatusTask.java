package com.h9.lottery.task;

import com.h9.common.db.entity.lottery.Reward;
import com.h9.common.db.repo.RewardRepository;
import com.h9.lottery.provider.FactoryProvider;
import com.h9.lottery.provider.model.LotteryModel;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:变更订单状态
 * FactoryStatusTask:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/16
 * Time: 11:15
 */
@Component
public class FactoryStatusTask {


     Logger logger = Logger.getLogger(FactoryStatusTask.class);


    @Resource
    private RewardRepository rewardRepository;
    
    @Resource
    private FactoryProvider factoryProvider;


    @Scheduled(cron = "0 0 0/1 * * *")
    public void run() {
        List<Reward> factoryStatus = rewardRepository.findFactoryStatus();
        for (int i = 0; i < factoryStatus.size(); i++) {
            try {
                Reward reward = factoryStatus.get(i);
                LotteryModel lotteryModel = factoryProvider.updateLotteryStatus(reward.getCode());
                reward.setFactoryStatus(lotteryModel.getState());
                rewardRepository.save(reward);
            } catch (Exception e) {
                logger.debugv(e.getMessage(),e);
            }
        }


    }



}
