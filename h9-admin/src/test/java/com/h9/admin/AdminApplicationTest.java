package com.h9.admin;

import com.h9.admin.job.HotelOrderJob;
import com.h9.admin.service.ActivityService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.repo.WithdrawalsRecordRepository;
import com.h9.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTest {


    @Resource
    private ActivityService activityService;
    @Resource
    private HotelOrderJob job;

    @Test
    public void test() {
        job.scan();
    }


    @Resource
    private RedisBean redisBean;
    @Test
    public void start() {
//        activityService.startBigRichLottery();

//        activityService.startBigRichLottery();
        redisBean.setStringValue("123", "", 1, TimeUnit.MICROSECONDS);
    }

    @Test
    public void start2() {
//        activityService.startBigRichLottery();

        activityService.method2();
    }

    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordRepository;

}
