package com.transfer;

import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.UserBank;
import com.h9.common.db.repo.BankTypeRepository;
import com.h9.common.db.repo.GoodsReposiroty;
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
    private GoodsInfoService goodsInfoService;

    @Test
    public void userCard() {
        userService.userCard();
    }

    @Test
    public void transferBankType(){
        try {
            cardInfoService.readBanTypePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void transferCardInfo(){
        try {
            cardInfoService.readBankINfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Resource
    private OrderService orderService;
    @Test
    public void transferOrders(){
        orderService.trants();
    }


    @Test
    public void transferGoodsInfo(){
       goodsInfoService.trants();
    }

    @Test
    public void transferDidiCard() {
        diDiCardService.transferDidiCard();
    }


}
