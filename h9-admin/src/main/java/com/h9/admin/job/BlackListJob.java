package com.h9.admin.job;

import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * description: 描述查询黑名单用户
 *
 * 满足以下条件之一的用户就加入黑名单
 * 1.日平均开瓶数>=8且参与天数>=5且提现金额大于>=260
 * 2.非饮酒时间参与次数/总参与次数>=0.33且总参与次数>=70且提现金额大于>=260
 */
@Component
public class BlackListJob {

    private Logger logger = Logger.getLogger(this.getClass());
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scan(){
        logger.info("BlackListJob");

        //select a1.* from (select user.id user_id,count(*) lottery_count ,sum(lottery.money) lottery_money  from user,lottery where lottery.user_id  = user.id  group by user.id ) a1 where a1.lottery_money > 260;

    }
}



