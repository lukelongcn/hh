package com.h9.lottery;

import com.h9.lottery.service.CodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryTest:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 14:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryTest {

    @Resource
    private CodeService codeService;


    @Test
    public void contextLoads() {
        codeService.productCode();
    }
}
