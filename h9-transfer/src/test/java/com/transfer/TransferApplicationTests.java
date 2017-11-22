package com.transfer;

import com.transfer.service.AddressService;
import com.transfer.service.DiDiCardService;
import com.transfer.service.UserService;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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


}
