package com.transfer;

import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankTypeRepository;
import com.transfer.db.repo.CardInfoRepository;
import com.transfer.service.AddressService;
import com.transfer.service.CardInfoService;
import com.transfer.service.DiDiCardService;
import com.transfer.service.UserService;
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


    @Test
    public void contextLoads() {
        logger.debugv(" ----*************###############+++++++++++++++++++");

        userService.user();

        logger.debugv(" ----*************###############+++++++++++++++++++");
    }

    @Resource
    private AddressService addressService;

    @Test
    public void transferAddress() {
        addressService.transfernAddress();
    }
    @Resource
    private DiDiCardService diDiCardService;

    @Test
    public void transferDidiCard() {
        diDiCardService.transferDidiCard();
    }

    @Test
    public void userCard() {
        userService.userCard();
    }

    @Resource
    private CardInfoRepository cardInfoReposiroty;
    @Resource
    private CardInfoService cardInfoService;
    @Resource
    private BankTypeRepository bankTypeRepository;
    @Test
    public void transferBankType(){
        try {
            cardInfoService.readBanTypePage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void tsansferCardInfo(){
        try {
            cardInfoService.readBankINfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
