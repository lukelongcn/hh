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
public class SqlTests {

    private Logger logger = Logger.getLogger(SqlTests.class);
    @Resource
    private UserService userService;
    @Resource
    private AddressService addressService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private BounsDetailService bounsDetailService;
    @Resource
    private OratransSerivce oratransSerivce;

    @Test
    public void user() {
        logger.debugv(" ----*************###############+++++++++++++++++++");
        userService.user();
        logger.debugv(" ----*************###############+++++++++++++++++++");
    }

    @Test
    public void transferAddress() {
        addressService.transfernAddress();
    }

    @Test
    public void transferVcoins(){
        integralRecordService.trants();
    }


    @Test
    public void transferOratrans(){
        oratransSerivce.trants();
    }



    @Test
    public void transferBouns(){
        bounsDetailService.trants();
    }





}
