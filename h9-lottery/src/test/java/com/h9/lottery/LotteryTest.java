package com.h9.lottery;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.Activity;
import com.h9.common.db.entity.Product;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.repo.ActivityRepository;
import com.h9.common.db.repo.ProductRepository;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.MD5Util;
import com.h9.lottery.utils.CodeUtil;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.UUID;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * LotteryTest:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 14:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TransactionConfiguration(defaultRollback=false)
public class LotteryTest {
     Logger logger = Logger.getLogger(LotteryTest.class);

    @Resource
    private RewardRepository rewardRepository;
    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ProductRepository productRepository;




    @Test
    @Transactional
    public void contextLoads() {

        Activity one = activityRepository.findOne(8L);
        Product product = productRepository.findOne(1L);
        for(int i=0;i<=1000;i++) {
            try {
                Reward reward = new Reward();
                reward.setMoney(new BigDecimal(18));
                String uuid = UUID.randomUUID().toString();
                String shortUrl = CodeUtil.shortUrl(uuid, CodeUtil.genRandomStrCode(8));
                reward.setCode(shortUrl);
                logger.debugv(shortUrl);
                reward.setActivity(one);
                reward.setMd5Code(MD5Util.getMD5(shortUrl));
                reward.setProduct(product);
                Reward reward1 = rewardRepository.saveAndFlush(reward);
                logger.debugv(shortUrl+ " " +JSONObject.toJSONString(reward1));
            } catch (Exception e) {
                logger.debug(e.getMessage(),e);;
            } finally {
            }
        }
        logger.debugv("完成");
    }





}
