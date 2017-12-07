package com.transfer;

import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankTypeRepository;
import com.transfer.db.entity.Oratrans;
import com.transfer.db.repo.CardInfoRepository;
import com.transfer.db.repo.IntegralRecordRepository;
import com.transfer.service.*;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferApplicationTests {

    private Logger logger = Logger.getLogger(TransferApplicationTests.class);
    @Resource
    private UserService userService;
    @Resource
    private DiDiCardService diDiCardService;
    @Resource
    private CardInfoService cardInfoService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private BounsDetailService bounsDetailService;
    @Resource
    private BounsService bounsService;
    @Resource
    private BlackListService blackListService;
    @Resource
    private AddressService addressService;
    @Resource
    private AreaPhoneService areaPhoneService;

    @Test
    public void initUser() {
        long start = System.currentTimeMillis();
        userService.user();
        long end = System.currentTimeMillis();
        logger.debugv("end - start"+(end-start));
    }

    @Test
    public void initAddress() {
        long start = System.currentTimeMillis();
        addressService.transfernAddress();
        long end = System.currentTimeMillis();
        logger.debugv("end - start"+(end-start));
    }


    @Test
    public void initDIDICard() {
        long start = System.currentTimeMillis();
        diDiCardService.transferDidiCard();
        long end = System.currentTimeMillis();
        logger.debugv("end - start"+(end-start));
    }

    @Test
    public void initUserCard() {
        long start = System.currentTimeMillis();
        userService.userCard();
        long end = System.currentTimeMillis();
        logger.debugv("end - start"+(end-start));
    }


    @Test
    public void transferBankType(){
        try {
            long start = System.currentTimeMillis();
            cardInfoService.readBanTypePage();
            long end = System.currentTimeMillis();
            logger.debugv("end - start"+(end-start));
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @Test
    public void tsansferCardInfo(){
        try {
            long start = System.currentTimeMillis();
            cardInfoService.readBankINfo();
            long end = System.currentTimeMillis();
            logger.debugv("end - start"+(end-start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void initVConins(){
        long start = System.currentTimeMillis();
        integralRecordService.trants();
        long end = System.currentTimeMillis();
        logger.debugv("end - start"+(end-start));
    }

    @Test
    public void transferBouns(){
        long start = System.currentTimeMillis();
        bounsDetailService.trants();
        long end = System.currentTimeMillis();
        logger.debugv("end - start"+(end-start));
    }


    @Test
    public void transferBounsFlow(){
        bounsService.trants();
    }


    @Test
    public void initBlackList(){
        blackListService.trants();
    }

    @Test
    public void initAreaPhone(){
        areaPhoneService.trants();
    }

    @Resource
    private OratransSerivce oratransSerivce;

    @Test
    public void initOratrans(){
        oratransSerivce.trants();
    }

}
