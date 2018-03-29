package com.h9.admin;

import com.h9.admin.job.HotelOrderJob;
import com.h9.admin.service.ActivityService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTest {


    @Resource
    private ActivityService activityService;
    @Resource
    private HotelOrderJob job;
    @Test
    public void test(){
        job.scan();
    }

    @Test
    public void start(){
        activityService.startBigRichLottery();
    }

    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordRepository;

}
