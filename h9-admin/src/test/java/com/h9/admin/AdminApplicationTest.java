package com.h9.admin;

import com.h9.admin.job.HotelOrderJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTest {


    @Resource
    private HotelOrderJob job;
    @Test
    public void test(){
        job.scan();
    }
}
