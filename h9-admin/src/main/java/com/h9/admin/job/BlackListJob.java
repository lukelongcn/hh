package com.h9.admin.job;

import com.h9.common.common.ConfigService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.SystemBlackList;
import com.h9.common.db.repo.LotteryRepository;
import com.h9.common.db.repo.SystemBlackListRepository;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    @Resource
    private ConfigService configService;
    @Resource
    private SystemBlackListRepository systemBlackListRepository;

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private LotteryRepository lotteryRepository;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void scan() {
        logger.info("BlackListJob go.................");

        long start = System.currentTimeMillis();
        List<Object> blackUser = lotteryRepository.findBlackUser();
        long end = System.currentTimeMillis();
        logger.info("黑名单查询时间：" + (end - start) / 1000L + " 秒");

        List<Long> userIdList = blackUser.stream()
                .map(el -> Long.valueOf(el.toString()))
                .collect(Collectors.toList());

        int size = userIdList.size();
        for (int i = 0; i < size; i++) {

            Long userId = userIdList.get(i);
            Date startDate = new Date();
            Integer blackListDisabledTime = Integer.valueOf(configService.getStringConfig("blackListDisabledTime"));
            Date endDate = DateUtil.getDate(startDate, blackListDisabledTime, Calendar.HOUR_OF_DAY);
            SystemBlackList blackList = new SystemBlackList(userId, startDate, endDate, 1, "黑名单", null, null, null);

            SystemBlackList systemBlackList = systemBlackListRepository.findByUserIdAndStatus(userId, new Date());
            if (systemBlackList == null) {
                logger.info("黑名单userId : "+userId);
                systemBlackListRepository.save(blackList);
            }
        }
    }

}



