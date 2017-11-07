package com.h9.lottery.service;

import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.*;
import com.h9.common.db.entity.Reward.StatusEnum;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.NetworkUtil;
import com.h9.lottery.model.dto.LotteryFlowDTO;
import com.h9.lottery.model.dto.LotteryResult;
import com.h9.lottery.model.dto.LotteryUser;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.model.vo.LotteryResultDto;
import com.h9.lottery.utils.RandomDataUtil;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.Reward.StatusEnum.END;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * LotteryService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 14:50
 */
@Service
public class LotteryService {

    private static int dayMaxotteryCount = 6;
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
    @Resource
    private RandomDataUtil randomDataUtil;
    @Resource
    private LotteryFlowRepository lotteryFlowRepository;
    @Resource
    private CommonService commonService;

    @Transactional
    public Result appCode(Long userId, LotteryDto lotteryVo, HttpServletRequest request) {
//        记录用户信息
        UserRecord userRecord = newUserRecord(userId, lotteryVo, request);
        //TODO 检查用户是否在黑名单里面
        //检查用户参与活动次数,是否超标
        Date startDate = new Date();
        Date monthmorning = DateUtil.getTimesMonthmorning(startDate);
        Date timesMonthnight = DateUtil.getTimesMonthnight(startDate);
        BigDecimal lotteryCount = lotteryLogRepository.getLotteryCount(userId, monthmorning, timesMonthnight);

        if (lotteryCount == null) {
            lotteryCount = new BigDecimal(0);
        }
        //TODO 写成配置的
        BigDecimal userLotteryCount = lotteryLogRepository.getLotteryCount(userId, lotteryVo.getCode());
        if (lotteryCount.compareTo(new BigDecimal(dayMaxotteryCount)) > 0
                && userLotteryCount.compareTo(new BigDecimal(0)) <= 0) {
//            这个码没有被扫过，是新码,并且当天数量超标了
            return Result.fail("您的扫码数量已经超过当天限制了");
        }

        //TODO 检查第三方库有没有数据
        Reward reward = rewardRepository.findByCode4Update(lotteryVo.getCode());
        //记录扫码记录
        record(userId, reward, lotteryVo, userRecord);
        if (reward == null) {
            return Result.fail("奖励条码不存在");
        }
        Integer status = reward.getStatus();
        if (status == StatusEnum.FAILD.getCode()) {
            return Result.fail("奖励已经失效");
        }
        Lottery lottery = lotteryRepository.findByUserIdAndReward(userId, reward);
        if (lottery != null) {
//          放回是否开奖
            LotteryResultDto lotteryResultDto = new LotteryResultDto();
            lotteryResultDto.setRoomUser(lottery.getRoomUser() == 1);
            lotteryResultDto.setLottery(reward.getStatus() == StatusEnum.END.getCode());
            return Result.success(lotteryResultDto);
        } else {
//        如果没有参加，参加活动
            if (status == END.getCode()) {
//                如果已经结束
                return Result.fail("红包活动已经结束");
            }
            LotteryResultDto lotteryResultDto = new LotteryResultDto();
            lotteryResultDto.setLottery(false);
            lotteryResultDto.setRoomUser(false);
            lotteryResultDto.setLottery(reward.getStatus() == StatusEnum.END.getCode());
            //是第一个用户
            lottery = new Lottery();
            int partakeCount = reward.getPartakeCount();
            if (partakeCount == 0) {
                reward.setUserId(userId);
                lottery.setRoomUser(2);
                lotteryResultDto.setRoomUser(true);
            }
            reward.setPartakeCount(partakeCount + 1);
            rewardRepository.save(reward);
            lottery.setReward(reward);
            lottery.setUserId(userId);
            lottery.setUserRecord(userRecord);
            lotteryRepository.save(lottery);
            return Result.success(lotteryResultDto);
        }

    }

    public void record(Long userId, Reward reward, LotteryDto lotteryVo, UserRecord userRecord) {
        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setUserId(userId);
        lotteryLog.setUserRecord(userRecord);
        lotteryLog.setCode(lotteryVo.getCode());
        if (reward == null) {
            lotteryLog.setStatus(2);
        } else {
            lotteryLog.setReward(reward);
        }
        lotteryLogRepository.save(lotteryLog);
    }


    public UserRecord newUserRecord(Long userId, LotteryDto lotteryVo, HttpServletRequest request) {
        UserRecord userRecord = new UserRecord();

        String refer = request.getHeader("Referer");
        String userAgent = request.getHeader("User-Agent");
        userRecord.setUserId(userId);
        userRecord.setUserAgent(userAgent);
        userRecord.setRefer(refer);
        String ip = NetworkUtil.getIpAddress(request);
        userRecord.setIp(ip);
        String client = request.getHeader("client");
        if (StringUtils.isNotEmpty(client)) {
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
            Long userId, String code) {
        Reward reward = rewardRepository.findByCode(code);
        if (reward == null) {
            return Result.fail("红包不存在");
        }
        Lottery lottery = lotteryRepository.findByUserIdAndReward(userId, reward);
        if (lottery == null || reward.getPartakeCount() == 0) {
            return Result.fail("您没有参与该活动");
        }

        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setCode(code);

        Date nowDate = new Date();
        String nowTime = DateUtil.formatDate(nowDate, DateUtil.FormatType.SECOND);
        lotteryResult.setNowTime(nowTime);

        Date updateTime = lotteryRepository.findByRewardLastTime(reward);
        //TODO 暂时设置为 5 分钟
        Date lastDate = DateUtil.getDate(updateTime, 5, Calendar.MINUTE);
        String endTime = DateUtil.formatDate(lastDate, DateUtil.FormatType.SECOND);
        lotteryResult.setEndTime(endTime);

        if(lastDate.before(nowDate)){
            lottery(null, code);
        }

        Integer status = reward.getStatus();
        boolean islottery = status == StatusEnum.END.getCode();
        lotteryResult.setLottery(islottery);
        lotteryResult.setRoomUser(userId.equals(reward.getUserId()));

        //TODO
        LotteryFlow lotteryFlow = lotteryFlowRepository.findByReward(reward, userId);
        if(lotteryFlow!=null){
            lotteryResult.setMoney(lotteryFlow.getMoney());
        }

        lotteryResult.setQrCode(""+code);
        List<LotteryUser> lotteryUsers = new ArrayList<>();
        if(islottery){
            List<LotteryFlow> flows = lotteryFlowRepository.findByReward(reward);
            for (int i = 0; i < flows.size(); i++) {
                LotteryFlow lotteryFromDb = flows.get(i);
                LotteryUser lotteryUser = new LotteryUser();
                lotteryUser.setRoomUser(lotteryFromDb.getRoomUser() == 2);
                lotteryUser.setUserId(lotteryFromDb.getUserId());
                lotteryUser.setMoney(lotteryFromDb.getMoney());

                User user = userRepository.findOne(lotteryFromDb.getUserId());
                lotteryUser.setName(user.getNickName());
                lotteryUser.setAvatar(user.getAvatar());
                lotteryUser.setMe(userId == lotteryUser.getUserId());
                lotteryUsers.add(lotteryUser);
            }
        }else{
            List<Lottery> lotteryList = lotteryRepository.findByReward(reward);
            for (int i = 0; i < lotteryList.size(); i++) {
                Lottery lotteryFromDb = lotteryList.get(i);
                LotteryUser lotteryUser = new LotteryUser();
                lotteryUser.setRoomUser(lotteryFromDb.getRoomUser() == 2);
                lotteryUser.setUserId(lotteryFromDb.getUserId());
                User user = userRepository.findOne(lotteryFromDb.getUserId());
                lotteryUser.setName(user.getNickName());
                lotteryUser.setAvatar(user.getAvatar());
                lotteryUser.setMe(userId == lotteryUser.getUserId());
                lotteryUsers.add(lotteryUser);
            }
        }
        lotteryResult.setLotteryUsers(lotteryUsers);

        return Result.success(lotteryResult);
    }

    @Transactional
    public Result lottery(Long curUserId,String code) {
        Reward reward = rewardRepository.findByCode4Update(code);
        if (reward == null) {
            return Result.fail("红包不存在");
        }
        if(curUserId!=null){
            if(!curUserId.equals(reward.getUserId())){
                return Result.fail("你无权操作");
            }
        }
        if (reward.getStatus() == END.getCode()) {
            return Result.success("红包已经抽取");
        }
        if (reward.getStatus() == StatusEnum.FAILD.getCode()) {
            return Result.success("红包已失效");
        }
        List<Lottery> lotteryList = lotteryRepository.findByReward(reward);
        if (CollectionUtils.isEmpty(lotteryList)) {
            return Result.fail("没有抽奖记录");
        }
        List<Lottery> lotteries = new ArrayList<>();
        List<LotteryFlow> lotteryFlows = getReward(reward, lotteryList, lotteries);
        lotteryRepository.save(lotteries);
        lotteryFlowRepository.save(lotteryFlows);
        //变更奖励状态
        reward.setStatus(END.getCode());
        //变更中奖用户余额
        reward.setHaveMoney(new BigDecimal(0));
        reward.setSurplusMoeny(reward.getMoney());
        rewardRepository.save(reward);
        //变更用余额
        for (int i = 0; i < lotteryFlows.size(); i++) {
            LotteryFlow lotteryFlow = lotteryFlows.get(i);
            Long lotteryUserId = lotteryFlow.getUserId();
            BigDecimal money = lotteryFlow.getMoney();
            commonService.setBalance(lotteryUserId, money, 1L, lotteryFlow.getId(), lotteryFlow.getId() + "","抢红包获取余额");
        }
        return Result.success();
    }



    /****
     * 生成奖励人员
     * @param reward
     * @param lotteryList
     * @param lotteries
     * @return
     */
    @Transactional
    public List<LotteryFlow> getReward(Reward reward, List<Lottery> lotteryList,List<Lottery> lotteries) {
        BigDecimal money = reward.getMoney();
        int size = lotteryList.size();
        Map<Integer, BigDecimal> moneyMap = new HashMap<>();

        if (size == 1) {
            moneyMap.put(1, money);
        } else if (size == 2) {
            moneyMap.put(1, money.multiply(new BigDecimal(70)).divide(new BigDecimal(100)));
            moneyMap.put(2, money.multiply(new BigDecimal(30)).divide(new BigDecimal(100)));
        } else {
            moneyMap.put(1, money.multiply(new BigDecimal(40)).divide(new BigDecimal(100)));
            moneyMap.put(2, money.multiply(new BigDecimal(30)).divide(new BigDecimal(100)));
            moneyMap.put(3, money.multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
        }
        //获取随机中奖人数
        List<Lottery> lotteriesRandom = randomDataUtil.generateRandomPermutation(lotteryList, size <= 3 ? size : 3);
        lotteries.addAll(lotteriesRandom);
        List<LotteryFlow> lotteryFlows = new ArrayList<>();
        Random random = new Random();
        int count = list.size();
        for (int i = 0; i < lotteries.size(); i++) {
            BigDecimal rewardMoney = moneyMap.get(i + 1);
            Lottery lottery = lotteries.get(i);
            lottery.setMoney(rewardMoney);
            LotteryFlow lotteryFlow = new LotteryFlow(lottery);
            lotteryFlow.setRemarks(list.get(i % count));
            lotteryFlows.add(lotteryFlow);
        }
        return lotteryFlows;
    }

    static List<String> list = new ArrayList<>();

    static{
        list.add("一杯高炉酒，红包啥都有");
        list.add("换个姿势抢，红包会更多");
        list.add("与君共饮一杯酒！");
    }




    public Result<List<LotteryFlowDTO>> history(Long userId){
        List<LotteryFlow> lotteryFlows = lotteryFlowRepository.findByReward(userId);
        List<LotteryFlowDTO> lotteryFlowDTOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(lotteryFlows)){
            lotteryFlowDTOS = lotteryFlows.stream().map(LotteryFlowDTO::new).collect(Collectors.toList());
        }
        return Result.success(lotteryFlowDTOS);
    }




}
