package com.h9.admin;

import com.h9.admin.job.HotelOrderJob;
import com.h9.admin.service.ActivityService;
import com.h9.admin.service.POIService;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.UserCouponsRepository;
import com.h9.common.db.repo.WithdrawalsRecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
    private POIService poiService;

    @Resource
    private RedisBean redisBean;

    @Resource
    private CommonService commonService;

    @Test
    public void start() throws FileNotFoundException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert_wx.p12");
        System.out.println("");

    }

    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private UserCouponsRepository userCouponsRepository;

    @Test
    public void start2() {

    }

    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordRepository;

}
