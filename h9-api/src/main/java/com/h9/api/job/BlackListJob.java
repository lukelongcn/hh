package com.h9.api.job;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.repo.LotteryRepository;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description: 描述查询黑名单用户
 * <p>
 * 满足以下条件之一的用户就加入黑名单
 * 1.日平均开瓶数>=8且参与天数>=5且提现金额大于>=260
 * 2.非饮酒时间参与次数/总参与次数>=0.33且总参与次数>=70且提现金额大于>=260
 */
@Component
public class BlackListJob {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private LotteryRepository lotteryRepository;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void scan() {
        logger.info("BlackListJob");

        //select a1.* from (select user.id user_id,count(*) lottery_count ,sum(lottery.money) lottery_money  from user,lottery where lottery.user_id  = user.id  group by user.id ) a1 where a1.lottery_money > 260;
        List<?> blackUser = lotteryRepository.findBlackUser();

        Map<Integer, List<UserLotteryInfo>> byTimeGroupLMap = blackUser.stream().map(el -> {
            String jsonStr = JSONObject.toJSONString(blackUser);
            return JSONObject.parseObject(jsonStr, UserLotteryInfo.class);
        }).collect(Collectors.groupingBy(el -> el.getLottery_count()));


        byTimeGroupLMap.forEach((k,v) -> {

        });
        System.out.println(blackUser);
    }

    @Data
    @Accessors(chain = true)
    private static class UserLotteryInfo {
        private Long userId;
        private Integer lottery_count;
        private BigDecimal lottery_money;
    }
}



