package com.transfer;

import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankTypeRepository;
import com.h9.common.db.repo.GoodsReposiroty;
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

    @Resource
    private IntegralRecordService integralRecordService;


    @Test
    public void transferData(){
        integralRecordService.trants();
    }

    @Resource
    private BounsDetailService bounsDetailService;

    @Test
    public void transferBouns(){
        bounsDetailService.trants();
    }

    @Resource
    private BounsService bounsService;


    @Test
    public void transferBounsFlow(){
        bounsService.trants();
    }

    @Resource
    private OrderService orderService;
    @Test
    public void transferOrders(){
        orderService.trants();
    }

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsInfoService goodsInfoService;
    @Test
    public void transferGoodsInfo(){
       goodsInfoService.trants();
    }


}
