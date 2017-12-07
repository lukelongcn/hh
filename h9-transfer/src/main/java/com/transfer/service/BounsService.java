package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.MD5Util;
import com.transfer.db.entity.Bouns;
import com.transfer.db.entity.BounsDetails;
import com.transfer.db.repo.BounsDetailsRepository;
import com.transfer.db.repo.BounsRepository;
import com.transfer.service.base.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:转换为流水类
 * BounsService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/28
 * Time: 14:50
 */
@Component
public class BounsService extends BaseService<BounsDetails> {
    @Resource
    private BounsDetailsRepository  bounsDetailsRepository;
    @Resource
    private BounsRepository bounsRepository;
    @Resource
    private RewardRepository rewardRepository;
    @Resource
    private LotteryFlowRepository lotteryFlowRepository;
    @Resource
    private LotteryRepository lotteryRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRecordRepository userRecordRepository;



    @Override
    public String getPath() {
        //不需要写sql
        return null;
    }

    @Override
    public PageResult get(int page, int limit) {
        PageResult all = bounsDetailsRepository.findAll(page, limit);
        return all;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void getSql(BounsDetails bounsDetails, BufferedWriter userWtriter) throws IOException {
        if(bounsDetails.getBounsOID()==null){
            return;
        }
        User user = userRepository.findOne(bounsDetails.getUserid());
        if (user == null) {
            return;
        }
        Bouns bouns = bounsRepository.findOne(bounsDetails.getBounsOID());
        Reward reward = rewardRepository.findByCode(bouns.getCodeId());
        if(reward == null){
            reward = new Reward();
            reward.setActivityId(1L);
            reward.setMoney(bounsDetails.getUserBouns());
            reward.setCode(bouns.getCodeId());
            reward.setMd5Code(MD5Util.getMD5(bouns.getCodeId()));
            reward.setPartakeCount(1);
            reward.setStatus(Reward.StatusEnum.END.getCode());

        }else{
            reward.setPartakeCount(reward.getPartakeCount() + 1);
            reward.setMoney(reward.getMoney().add(bounsDetails.getUserBouns()));
        }
        reward = rewardRepository.saveAndFlush(reward);

        UserRecord userRecord = new UserRecord();
        userRecord.setImei(bounsDetails.getImei());
        userRecord.setVersion(bounsDetails.getVersion());
        userRecord.setUserId(bounsDetails.getUserid());
        userRecord = userRecordRepository.saveAndFlush(userRecord);


        Lottery lottery = new Lottery();
        lottery.setUser(user);
        lottery.setMoney(bounsDetails.getUserBouns());
        lottery.setReward(reward);
        lottery.setStatus(3);
        lottery.setUserRecord(userRecord);
        lotteryRepository.save(lottery);

        LotteryFlow lotteryFlow = new LotteryFlow();
        BeanUtils.copyProperties(lottery,lotteryFlow,"id");
        lotteryFlow.setRemarks("");
        lotteryFlowRepository.save(lotteryFlow);
    }


    @Override
    public String getTitle() {
        return "中奖记录迁移进度";
    }
}
