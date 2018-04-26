package com.h9.admin;

import com.h9.admin.job.HotelOrderJob;
import com.h9.admin.model.dto.AddBigRichDTO;
import com.h9.admin.service.AccountService;
import com.h9.common.db.entity.bigrich.OrdersLotteryActivity;
import com.h9.common.db.repo.OrdersLotteryActivityRepository;
import com.h9.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;

/**
 * Created by Gonyb on 2017/11/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test666 {
    @Resource
    private AccountService accountService;

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        Long publish = jedis.publish("class", "hello");
        System.out.println(publish);
    }
}
