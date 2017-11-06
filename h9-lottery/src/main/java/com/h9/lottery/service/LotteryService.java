package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.NetworkUtil;
import com.h9.lottery.model.dto.LotteryResult;
import com.h9.lottery.model.dto.LotteryUser;
import com.h9.lottery.model.vo.LotteryDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Resource
    private UserRepository userRepository;
    


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
                lottery.setRoomUser(2);
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

    public void record(Long userId,Reward reward, LotteryDto lotteryVo, UserRecord userRecord) {
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
    }


    public UserRecord newUserRecord(Long userId, LotteryDto lotteryVo, HttpServletRequest request){
        UserRecord userRecord = new UserRecord();

        String refer = request.getHeader("Referer");
        String userAgent = request.getHeader("User-Agent");
        userRecord.setUserId(userId);
        userRecord.setUserAgent(userAgent);
        userRecord.setRefer(refer);
        String ip = NetworkUtil.getIpAddress(request);
        userRecord.setIp(ip);
        String client = request.getHeader("client");
        if(StringUtils.isNotEmpty(client)){
            userRecord.setClient(Integer.parseInt(client));
        }
        String version = request.getHeader("version");
        userRecord.setVersion(version);
        userRecord.setLatitude(lotteryVo.getLatitude());
        userRecord.setLongitude(lotteryVo.getLongitude());
        String imei = request.getHeader("imei");
        userRecord.setImei(imei);
        return userRecordRepository.saveAndFlush(userRecord);
    }


    public Result<LotteryResult> getLotteryRoom(
             long userId, String code) {
        Reward reward = rewardRepository.findByCode(code);
        if(reward == null||reward.getPartakeCount()==0){
            return Result.fail("红包不存在");
        }
        Lottery lottery = lotteryRepository.findByUserIdAndReward(userId, reward);
        if(lottery == null){
            return Result.fail("您没有参与该活动");
        }

        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setCode(code);
        Date nowDate = new Date();
        String nowTime = DateUtil.formatDate(nowDate, DateUtil.FormatType.SECOND);
        lotteryResult.setNowTime(nowTime);

        List<Lottery> lotteryList = lotteryRepository.findByReward(reward);
        List<LotteryUser> lotteryUsers = new ArrayList<>();
        for (int i = 0; i < lotteryList.size(); i++) {
            Lottery lotteryFromDb = lotteryList.get(i);
            LotteryUser lotteryUser = new LotteryUser();
            lotteryUser.setRoomUser(lotteryFromDb.getRoomUser() == 2);

            lotteryUser.setUserId(lotteryFromDb.getUserId());
            User user = userRepository.findOne(userId);
            lotteryUser.setName(user.getNickName());
            lotteryUser.setAvatar(user.getAvatar());
            lotteryUser.setMe(userId == lotteryUser.getUserId());

            lotteryUsers.add(lotteryUser);
        }
        lotteryResult.setLotteryUsers(lotteryUsers);

        return Result.success(lotteryResult);
    }

    @Transactional
    public Result lottery(String code){
        Reward reward = rewardRepository.findByCode4Update(code);
        if (reward == null) {
            return Result.fail("红包不存在");
        }
        if(reward.getStatus()==3) {
            return Result.success("红包已经抽取");
        }
        if(reward.getStatus()==4) {
            return Result.success("红包已失效");
        }
        List<Lottery> lotteryList = lotteryRepository.findByReward(reward);
        if(CollectionUtils.isEmpty(lotteryList)){
            return Result.fail("没有抽奖记录");
        }
        int size = lotteryList.size();
        BigDecimal money = reward.getMoney();
        List<LotteryFlow> lotteryFlows = new ArrayList<>();
        if(size == 1){
            Lottery lottery = lotteryList.get(0);
            lottery.setMoney(money);
            LotteryFlow lotteryFlow = newLotteryFlow(lottery, money);
            lotteryFlows.add(lotteryFlow);
        }else if(size == 2){
            Lottery lottery = lotteryList.get(0);
            lottery.setMoney(money.multiply(new BigDecimal(70)).divide(new BigDecimal(100)));
            LotteryFlow lotteryFlow = newLotteryFlow(lottery, money);
            lotteryFlows.add(lotteryFlow);

        }else if(size == 3){

        }else{

        }


        return Result.success();
    }


    public LotteryFlow newLotteryFlow(Lottery lottery, BigDecimal money)
    {
        LotteryFlow lotteryFlow = new LotteryFlow();
        BeanUtils.copyProperties(lottery,lotteryFlow,"id");
        return lotteryFlow;
    }







}
