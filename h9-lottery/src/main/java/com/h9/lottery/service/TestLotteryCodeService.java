package com.h9.lottery.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.Result;
import com.h9.common.db.entity.lottery.Reward;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.MD5Util;
import com.h9.lottery.utils.CodeUtil;

import org.jboss.logging.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import static org.jboss.logging.Logger.getLogger;

/**
 * Created by 李圆 on 2017/12/18
 */
@Component
public class TestLotteryCodeService {

    Logger logger = getLogger(TestLotteryCodeService.class);
    @Resource
    private RewardRepository rewardRepository;

    @Resource
    private Environment environment;

    @Transactional
    public Result createCode() {
        String osName = environment.getProperty("h9.current.envir");
        System.out.println(osName);
        if (!osName .equals("test")){
           return Result.fail("当前不为测试环境");
        }
        for(int i=0;i<=50;i++) {
            try {
                Reward reward = new Reward();
                reward.setMoney(new BigDecimal(18));
                String uuid = UUID.randomUUID().toString();
                String shortUrl = CodeUtil.shortUrl(uuid, CodeUtil.genRandomStrCode(8));
                reward.setCode(shortUrl);
                logger.debugv(shortUrl);
                reward.setActivityId(1L);
                reward.setMd5Code(MD5Util.getMD5(shortUrl));
                Reward reward1 = rewardRepository.saveAndFlush(reward);
                logger.debugv(shortUrl+ " " + JSONObject.toJSONString(reward1));
            } catch (Exception e) {
                logger.debug(e.getMessage(),e);
            } finally {
            }
        }
        logger.debugv("完成");

        List<String> codelist = rewardRepository.findByStatus();
        return Result.success("当前未使用的红包码为",codelist);
    }
}
