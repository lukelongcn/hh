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
public class DataTests {

    private Logger logger = Logger.getLogger(DataTests.class);

    @Resource
    private BounsService bounsService;
    @Resource
    private BlackListService   blackListService;
    @Resource
    private AreaPhoneService areaPhoneService;
    @Resource
    private TastingService tastingService;


    @Test
    public void transferBlackList(){
        blackListService.trants();
    }

    @Test
    public void transferAreaPhone(){
        areaPhoneService.trants();
    }

    @Test
    //TODO 要改
    public void transferTastingService(){
        tastingService.trants();
    }

    @Test
    public void transferBounsFlow(){
        bounsService.trants();
    }



}
