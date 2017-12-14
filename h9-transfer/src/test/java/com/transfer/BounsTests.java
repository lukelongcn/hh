package com.transfer;

import com.transfer.service.BounsService;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BounsTests {

    private Logger logger = Logger.getLogger(BounsTests.class);
    @Resource
    private BounsService bounsService;

    @Test
    public void transferBouns1(){
        bounsService.trants(1,1000,null);
    }
    @Test
    public void transferBouns2(){
        bounsService.trants(1001,2000,null);
    }
    @Test
    public void transferBouns3(){
        bounsService.trants(2001,3000,null);
    }
    @Test
    public void transferBouns4(){
        bounsService.trants(3001,4000,null);
    }
    @Test
    public void transferBouns5(){
        bounsService.trants(4000,5000,null);
    }

    @Test
    public void transferBouns6(){
        bounsService.trants(5001,null,null);
    }


}
