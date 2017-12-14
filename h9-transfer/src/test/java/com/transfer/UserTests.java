package com.transfer;

import com.transfer.service.*;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    private Logger logger = Logger.getLogger(UserTests.class);
    @Resource
    private UserMainService userMainService;


    @Test
    public void transferUser() {
        userMainService.trants(1,50,null);
    }

    @Test
    public void transferUser1() {
        userMainService.trants(51,100,null);
    }


    @Test
    public void transferUser2() {
        userMainService.trants(101,150,null);
    }

    @Test
    public void transferUser3() {
        userMainService.trants(151,200,null);
    }

    @Test
    public void transferUser4() {
        userMainService.trants(201,250,null);
    }

    @Test
    public void transferUser5() {
        userMainService.trants(251,300,null);
    }

    @Test
    public void transferUser300() {
        userMainService.trants(300,340,null);
    }

    @Test
    public void transferUser320() {
        userMainService.trants(320,340,null);
    }

    @Test
    public void transferUser340() {
        userMainService.trants(340,360,null);
    }

    @Test
    public void transferUser360() {
        userMainService.trants(360,null,null);
    }

}
