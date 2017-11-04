package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.entity.UserRecord;
import com.h9.common.db.repo.RewardRepository;
import com.h9.common.utils.NetworkUtil;
import com.h9.lottery.model.vo.LotteryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private RewardRepository rewardRepository;


    public Result appCode(Long userId, LotteryVo lotteryVo,HttpServletRequest request){

//        记录用户信息

        //检查用户是否在黑名单里面
        Reward reward = rewardRepository.findByCode(lotteryVo.getCode());
        if (reward == null) {
           Long count = record(userId, lotteryVo);
            //次数到达多少次 加如黑名单



            return Result.fail("奖励条码不存在");
        }






        return Result.success();
    }

    public Long record(Long userId,LotteryVo lotteryVo){
//        TODO 记录领取异常的 达到一定数量 加如黑名单




        return 0l;
    }


    public UserRecord newUserRecord(Long userId,LotteryVo lotteryVo,HttpServletRequest request){
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


        return userRecord;

    }






}
