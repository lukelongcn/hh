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
public class BounsDetailSqlTests {

    private Logger logger = Logger.getLogger(BounsDetailSqlTests.class);
    @Resource
    private BounsDetailService bounsDetailService;



    @Test
    public void transferBounsDetails(){
        bounsDetailService.trants();
    }



    @Test
    public void transferBounsDetail05(){
        bounsDetailService.trants(1,500,"./sql/bounsDetail_05.sql");
    }
    @Test
    public void transferBounsDetail10(){
        bounsDetailService.trants(501,1000,"./sql/bounsDetail_10.sql");
    }
    @Test
    public void transferBounsDetail15(){
        bounsDetailService.trants(1001,1500,"./sql/bounsDetail_15.sql");
    }
    @Test
    public void transferBounsDetail20(){
        bounsDetailService.trants(1501,2000,"./sql/bounsDetail_20.sql");
    }
    @Test
    public void transferBounsDetail25(){
        bounsDetailService.trants(2410,2500,"./sql/bounsDetail5_25.sql");
    }
    @Test
    public void transferBounsDetail30(){
        bounsDetailService.trants(2910,3000,"./sql/bounsDetail5_30.sql");
    }
    @Test
    public void transferBounsDetail35(){
        bounsDetailService.trants(3349,3500,"./sql/bounsDetail5_35.sql");
    }
    @Test
    public void transferBounsDetail40(){
        bounsDetailService.trants(3775,4000,"./sql/bounsDetail5_40.sql");
    }
    @Test
    public void transferBounsDetail50(){
        bounsDetailService.trants(4685,5000,"./sql/bounsDetail5_50.sql");
    }
    @Test
    public void transferBounsDetail55(){
        bounsDetailService.trants(5001,null,"./sql/bounsDetail5_55.sql");
    }


}
