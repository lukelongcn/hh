package com.transfer;

import com.h9.common.db.repo.BankTypeRepository;
import com.h9.common.db.repo.GoodsReposiroty;
import com.transfer.db.repo.CardInfoRepository;
import com.transfer.service.*;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {

    private Logger logger = Logger.getLogger(UserApplicationTests.class);
    @Resource
    private UserService userService;
    @Resource
    private AddressService addressService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private BounsDetailService bounsDetailService;
    @Resource
    private BounsService bounsService;
    @Resource
    private BlackListService   blackListService;
    @Resource
    private AreaPhoneService areaPhoneService;
    @Resource
    private OratransSerivce oratransSerivce;
    @Resource
    private TastingService tastingService;
    @Resource
    private UserMainService userMainService;

    @Test
    public void user() {
        logger.debugv(" ----*************###############+++++++++++++++++++");
        userService.user();
        logger.debugv(" ----*************###############+++++++++++++++++++");
    }


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
    public void transferUser6() {
        userMainService.trants(300,null,null);
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
    public void transferBlackList(){
        blackListService.trants();
    }

    @Test
    public void transferAreaPhone(){
        areaPhoneService.trants();
    }

    @Test
    public void transferOratrans(){
        oratransSerivce.trants();
    }

    @Test
    public void transferTastingService(){
        tastingService.trants();
    }

    @Test
    public void transferBouns(){
        bounsDetailService.trants();
    }


    @Test
    public void transferBounsFlow(){
        bounsService.trants();
    }



}
