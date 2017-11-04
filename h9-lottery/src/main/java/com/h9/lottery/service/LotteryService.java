package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.Lottery;
import com.h9.common.db.entity.LotteryLog;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.entity.UserRecord;
import com.h9.common.db.repo.LotteryLogRepository;
import com.h9.common.db.repo.LotteryRepository;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.db.repo.UserRecordRepository;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.NetworkUtil;
import com.h9.lottery.model.vo.LotteryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 14:50
 */
@Service
public class LotteryService {

    @Resource
    UserRecordRepository userRecordRepository;
    @Resource
    private RewardRepository rewardRepository;
    @Resource
    private LotteryRepository lotteryRepository;

    @Resource
    private LotteryLogRepository lotteryLogRepository;

    private static int dayMaxotteryCount = 6;


    @Transactional
    public Result appCode(Long userId, LotteryDto lotteryVo, HttpServletRequest request) {
//        记录用户信息
        UserRecord userRecord = newUserRecord(userId, lotteryVo, request);
        //TODO 检查用户是否在黑名单里面

        //检查用户参与活动次数,是否超标
        Date startDate = new Date();
        Date monthmorning = DateUtil.getTimesMonthmorning(startDate);
        Date timesMonthnight = DateUtil.getTimesMonthnight(startDate);
        int lotteryCount = lotteryLogRepository.getLotteryCount(userId, monthmorning, timesMonthnight);
        //TODO 写成配置的
        if(lotteryCount>=dayMaxotteryCount){
            return Result.fail("您的扫码数量已经超过当天限制了");
        }
        Reward reward = rewardRepository.findByCode4Update(lotteryVo.getCode());
        //记录扫码记录
        record(userId, reward, lotteryVo, userRecord);
        if(reward == null){
            return Result.fail("奖励条码不存在");
        }
        Integer status = reward.getStatus();
        if (status == Reward.StatusEnum.FAILD.getCode()) {
            return Result.fail("奖励已经失效");
        }
        Lottery lottery = lotteryRepository.findByUserIdAndReward(userId, reward);
        if (lottery != null) {
//         如果已经参加，放回房间号
            return Result.success();
        } else {
//        如果没有参加，参加活动
            if (status == Reward.StatusEnum.END.getCode()) {
//                如果已经结束
                return Result.fail("红包活动已经结束");
            }
            //是第一个用户
            int partakeCount = reward.getPartakeCount();
            if (partakeCount == 0) {
                reward.setUserId(userId);
            }
            reward.setPartakeCount(partakeCount + 1);
            rewardRepository.save(reward);
            lottery = new Lottery();
            lottery.setReward(reward);
            lottery.setUserId(userId);
            lottery.setUserRecord(userRecord);
            lotteryRepository.save(lottery);
        }
        return Result.success();
    }

    public Long record(Long userId,Reward reward, LotteryDto lotteryVo, UserRecord userRecord) {
        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setUserId(userId);
        lotteryLog.setUserRecord(userRecord);
        lotteryLog.setCode(lotteryVo.getCode());
        if (reward == null) {
            lotteryLog.setStatus(2);
        }else{
            lotteryLog.setReward(reward);
        }
        lotteryLogRepository.save(lotteryLog);

        return 0l;
    }


    public UserRecord newUserRecord(Long userId, LotteryDto lotteryVo, HttpServletRequest request){
        String refer = request.getHeader("Referer");
        String userAgent = request.getHeader("User-Agent");
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);
        userRecord.setUserAgent(userAgent);
        userRecord.setRefer(refer);
        String ip = NetworkUtil.getIpAddress(request);
        userRecord.setIp(ip);
        userRecord.setClient(lotteryVo.getClient());
        userRecord.setImei(lotteryVo.getImei());
        userRecord.setLatitude(lotteryVo.getLatitude());
        userRecord.setLongitude(lotteryVo.getLongitude());
        userRecord.setImei(lotteryVo.getImei());
        return userRecordRepository.saveAndFlush(userRecord);

    }






}
