package com.h9.lottery;

import com.alibaba.fastjson.JSONObject;
import com.h9.lottery.utils.RandomDataUtil;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * LotteryTest:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 14:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryTest {
     Logger logger = Logger.getLogger(LotteryTest.class);

    @Test
    public void contextLoads() {

    }
    @Resource
    private RandomDataUtil randomDataUtil;

    @Test
    public void test() {

    }


}
