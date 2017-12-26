package com.h9.api.job;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.SystemBlackList;
import com.h9.common.db.repo.LotteryRepository;
import com.h9.common.db.repo.SystemBlackListRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private SystemBlackListRepository systemBlackListRepository;

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private LotteryRepository lotteryRepository;
    private String h9BlackListKey = "h9:blackList:userId:";
    @Resource
    private RedisBean redisBean;
    @Scheduled(cron = "0 0/5 * * * ?")
    public void scan() {
        logger.info("BlackListJob");

        long start = System.currentTimeMillis();
        List<Object> blackUser = lotteryRepository.findBlackUser();
        long end = System.currentTimeMillis();
        logger.info("黑名单查询时间："+(end - start) /1000L +" 秒");

        List<Long> userIdList = blackUser.stream()
                .map(el -> {
                    return Long.valueOf(blackUser.get(0).toString());
                })
                .collect(Collectors.toList());

        int size = userIdList.size();
        for(int i =0;i< size;i++) {

            Long userId = userIdList.get(i);
            SystemBlackList blackList = new SystemBlackList(userId,new Date(),null,1,"黑名单",null,null,null);
            String value = redisBean.getStringValue(h9BlackListKey + userId);

            if (StringUtils.isBlank(value)) {
                redisBean.setStringValue(h9BlackListKey + userId, "userId:"+userId);

                List<SystemBlackList> findUser = systemBlackListRepository.findByUserIdAndStatus(userId);
                if(findUser == null){
                    systemBlackListRepository.save(blackList);
                }
            }
        }
    }
//
//    @Data
//    @Accessors(chain = true)
//    @AllArgsConstructor
//    @NoArgsConstructor
//    private static class UserLotteryInfo {
//        private Long userId;
//        private Integer lotteryCount;
//        private BigDecimal lotteryMoney;
//        private Date createTime;
//        private Integer lotteryDayCount;
//    }
}



