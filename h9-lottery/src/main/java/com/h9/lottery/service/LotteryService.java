package com.h9.lottery.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.common.ServiceException;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.entity.Reward.StatusEnum;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MD5Util;
import com.h9.common.utils.MoneyUtils;
import com.h9.lottery.config.LotteryConstantConfig;
import com.h9.lottery.config.LotteryConfig;
import com.h9.lottery.model.dto.LotteryFlowDTO;
import com.h9.lottery.model.dto.LotteryResult;
import com.h9.lottery.model.dto.LotteryUser;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.model.vo.LotteryResultDto;
import com.h9.lottery.provider.FactoryProvider;
import com.h9.lottery.provider.model.LotteryModel;
import com.h9.lottery.provider.model.ProductModel;
import com.h9.lottery.utils.RandomDataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.BalanceFlow.FlowType.LOTTERY;
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

    Logger logger = Logger.getLogger(LotteryService.class);

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
    @Resource
    private ConfigService configService;
    @Resource
    private SystemBlackListRepository systemBlackListRepository;
    @Resource
    private LotteryConfig lotteryConfig;
    @Resource
    private WhiteUserListRepository whiteUserListRepository;
    @Resource
    private ProductTypeRepository productTypeRepository;
    @Resource
    private RedisBean redisBean;

    @Transactional(rollbackFor=Exception.class)
    public Result appCode(Long userId, LotteryDto lotteryVo, HttpServletRequest request) throws ServiceException {
//        记录用户信息
        UserRecord userRecord = commonService.newUserRecord(userId, lotteryVo.getLatitude(), lotteryVo.getLongitude(), request);
        //判断限定数量
        if (limitLotteryCount(userId, lotteryVo, request)) return Result.fail("已超过当日最大活动次数");
        //  检查第三方库有没有数据
        Result<Reward> result = exitsReward(lotteryVo.getCode());
        //记录扫码记录
        Reward data = result.getData();
        Long rewardId = data != null ? data.getId() : null;
        record(userId,rewardId , lotteryVo, userRecord);
        if (!result.isSuccess()) {
            return result;
        }
        Reward reward = data;
        if (reward == null) {
            return Result.fail("您扫的条码不存在");
        }
        Integer status = reward.getStatus();
        if (status == StatusEnum.FAILD.getCode()) {
            return Result.fail("奖励已经失效");
        }
        User user = userRepository.findOne(userId);
        String stringValue = redisBean.getStringValue(RedisKey.getLotteryBefore(userId, reward.getId()));
        if(StringUtils.isNotEmpty(stringValue)){
            return Result.fail("操作过于频繁，请稍后再试");
        }

        Lottery lottery = lotteryRepository.findByUserIdAndReward(userId, reward.getId());
        if (lottery != null) {
//          放回是否开奖
            LotteryResultDto lotteryResultDto = new LotteryResultDto();
            lotteryResultDto.setRoomUser(lottery.getRoomUser() == LotteryFlow.UserEnum.ROOMUSER.getId());
            lotteryResultDto.setLottery(reward.getStatus() == StatusEnum.END.getCode());
            return Result.success(lotteryResultDto);
        } else {
            //  s 第一次参数这个活动
            if (status == END.getCode()) {
//                如果已经 结束
                return Result.fail("红包活动已经结束");
            }
            redisBean.setStringValue(RedisKey.getLotteryBefore(userId, reward.getId()),reward.getPartakeCount()+"",1000, TimeUnit.MICROSECONDS);
            LotteryResultDto lotteryResultDto = new LotteryResultDto();
            lotteryResultDto.setLottery(false);
            lotteryResultDto.setRoomUser(false);
            lotteryResultDto.setLottery(reward.getStatus() == StatusEnum.END.getCode());
            //是第一个用户
            lottery = new Lottery();
            int partakeCount = lotteryRepository.findCountByReward(rewardId);
            lottery.setRoomUser(LotteryFlow.UserEnum.ORTHER.getId());
            if (partakeCount == 0) {
                lottery.setRoomUser(LotteryFlow.UserEnum.ROOMUSER.getId());
                lotteryResultDto.setRoomUser(true);
            }
            lottery.setReward(reward);
            lottery.setUser(user);
            lottery.setUserRecord(userRecord);
            lotteryRepository.save(lottery);

            //延长结束时间 finishTime
            Date endDate = DateUtil.getDate(new Date(), lotteryConfig.getDelay(), Calendar.SECOND);
            if (partakeCount == 0) {
                rewardRepository.updateReward(rewardId, endDate,userId);
            }else{
                rewardRepository.updateReward(rewardId, endDate);
            }
            return Result.success(lotteryResultDto);
        }
    }

    private boolean limitLotteryCount(Long userId, LotteryDto lotteryVo, HttpServletRequest request) {
        Date startDate = DateUtil.getTimesMorning();
        Date endDate = DateUtil.getTimesNight();
        // 当天的扫码数量限制
        BigDecimal lotteryCount = lotteryLogRepository.getTodayCount(userId,startDate,endDate);
        if (lotteryCount == null) {
            lotteryCount = new BigDecimal(0);
        }
        //  当前的条码扫描数量
        BigDecimal userLotteryCount = lotteryLogRepository.getCodeLotteryCount(userId, lotteryVo.getCode());
        if (userLotteryCount == null) {
            userLotteryCount = new BigDecimal(0);
        }
        logger.debugv("lotteryCount {0} userLotteryCount {1}", lotteryCount, userLotteryCount);
        String imei = request.getHeader("imei");
        // 如果扫旧码，就不用管了
        if(userLotteryCount.compareTo(new BigDecimal(0)) <= 0){
            boolean onWhiteUser = onWhiteUser(userId);
            boolean onBlackUser = onBlackUser(userId, imei);
            Integer daxMaxCount = null;
            if(onWhiteUser) {
                //不限制次数
                daxMaxCount = null;
            }else if (onBlackUser) {
                daxMaxCount = lotteryConfig.getBlackMaxLotteryCount();
            }else{
                daxMaxCount = lotteryConfig.getMaxLotteryCount();
            }
            if(daxMaxCount !=null ){
                if(lotteryCount.intValue() >= daxMaxCount ){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * description: 判断userId 或者Imei 在黑名单中
     */
    public boolean onBlackUser(Long userId, String imei) {

        List<SystemBlackList> systemBlackList = systemBlackListRepository.findByUserIdOrImei(userId, imei, new Date());

        if (CollectionUtils.isEmpty(systemBlackList)) {
            return false;
        }
        List<SystemBlackList> systemBlackLists = systemBlackList.stream().filter(user -> {
            Long startTime = user.getStartTime().getTime();
            Long endTime = user.getEndTime().getTime();
            Long currentDate = new Date().getTime();

            if (startTime < currentDate && currentDate < endTime) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        return org.apache.commons.collections.CollectionUtils.isNotEmpty(systemBlackLists);
    }

    public boolean onWhiteUser(Long userId) {
        List<WhiteUserList> userLists = whiteUserListRepository.findByUserId(userId, new Date());

        if(org.apache.commons.collections.CollectionUtils.isEmpty(userLists)){
            return false;
        }
        List<WhiteUserList> whiteUserLists = userLists.stream().filter(user -> {
            Long startTime = user.getStartTime().getTime();
            Long endTime = user.getEndTime().getTime();
            Long currentDate = new Date().getTime();

            if (startTime < currentDate && currentDate < endTime) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        return org.apache.commons.collections.CollectionUtils.isNotEmpty(whiteUserLists);
    }

    @Transactional
    private void record(Long userId, Long rewardId, LotteryDto lotteryVo, UserRecord userRecord) {
        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setUserId(userId);
        lotteryLog.setUserRecord(userRecord);
        lotteryLog.setCode(lotteryVo.getCode());
        if (rewardId == null) {
            lotteryLog.setStatus(2);
        } else {
            lotteryLog.setRewardId(rewardId);
        }
        lotteryLogRepository.save(lotteryLog);
    }

    @Transactional
    public Result<LotteryResult> getLotteryRoom(
            Long userId, String code) {
        code = LotteryConstantConfig.path2Code(code);
        Reward reward = rewardRepository.findByCode(code);
        if (reward == null) {
            return Result.fail("红包不存在");
        }
        Lottery lottery = lotteryRepository.findByUserIdAndReward(userId, reward.getId());
        if (lottery == null || reward.getPartakeCount() == 0) {
            return Result.fail("您没有参与该活动");
        }
        LotteryResult lotteryResult = new LotteryResult();

        User findUser = userRepository.findOne(userId);
        String tel = "";
        if (findUser != null) {
            tel = findUser.getPhone() == null ? "" : findUser.getPhone();
        }
        lotteryResult.setTel(tel);

        lotteryResult.setCode(code);
        lotteryResult.setRefreshTime(new BigDecimal(lotteryConfig.getLotteryRefresh()));
        lotteryResult.setUserCount(reward.getPartakeCount());
        Date nowDate = new Date();
        String nowTime = DateUtil.formatDate(nowDate, DateUtil.FormatType.SECOND);
        lotteryResult.setNowTime(nowTime);

        Date updateTime = lotteryRepository.findByRewardLastTime(reward);
        Date lastDate = DateUtil.getDate(updateTime, lotteryConfig.getDelay(), Calendar.SECOND);
        String endTime = DateUtil.formatDate(lastDate, DateUtil.FormatType.SECOND);
        lotteryResult.setEndTime(endTime);
        long differentDate = lastDate.getTime() - nowDate.getTime();
        lotteryResult.setDifferentDate(differentDate > 0 ? differentDate : 0);
        if (differentDate <= 0) {
            RLock lock = redisson.getLock("lock:" +  code);
            try {
                lock.lock(30, TimeUnit.SECONDS);
                logger.debugv("lottery start 中奖名单为：查询开奖 code:{0}" ,code);
                lottery(null, code);
            } finally {
                lock.unlock();
            }
        }
        Integer status = reward.getStatus();
        boolean islottery = status == StatusEnum.END.getCode();
        lotteryResult.setLottery(islottery);
        lotteryResult.setRoomUser(userId.equals(reward.getUserId()));
        //
        LotteryFlow lotteryFlow = lotteryFlowRepository.findByReward(reward, userId);
        if (lotteryFlow != null) {
            lotteryResult.setMoney(MoneyUtils.formatMoney(lotteryFlow.getMoney()));
        }

        lotteryResult.setQrCode(LotteryConstantConfig.Lottery_QR_PATH + code);
        List<LotteryUser> lotteryUsers = new ArrayList<>();
        if (islottery) {
            LotteryFlow top1LotteryFlow = lotteryFlowRepository.findTop1ByRewardOrderByMoneyDesc(reward);
            List<LotteryFlow> flows = lotteryFlowRepository.findByReward(reward);
            for (int i = 0; i < flows.size(); i++) {
                LotteryFlow lotteryFromDb = flows.get(i);
                LotteryUser lotteryUser = new LotteryUser();
                lotteryUser.setRoomUser(lotteryFromDb.getRoomUser() == LotteryFlow.UserEnum.ROOMUSER.getId());
                lotteryUser.setUserId(lotteryFromDb.getUser().getId());

                lotteryUser.setMoney(MoneyUtils.formatMoney(lotteryFromDb.getMoney()));

                lotteryUser.setDesc(lotteryFromDb.getDesc());
                lotteryUser.setMaxMoney(top1LotteryFlow.getId().equals(lotteryFromDb.getId()));
                lotteryUser.setCreateDate(DateUtil.toFormatDateString(lotteryFromDb.getCreateTime(), "HH:mm"));
                lotteryUser.setName(lotteryFromDb.getUser().getNickName());
                lotteryUser.setAvatar(lotteryFromDb.getUser().getAvatar());
                lotteryUser.setMe(lotteryFromDb.getUser().equals(lotteryUser.getUserId()));
                lotteryUsers.add(lotteryUser);
            }
        } else {
            List<Lottery> lotteryList = lotteryRepository.findByReward(reward);
            for (int i = 0; i < lotteryList.size(); i++) {
                Lottery lotteryFromDb = lotteryList.get(i);
                LotteryUser lotteryUser = new LotteryUser();
                lotteryUser.setRoomUser(lotteryFromDb.getRoomUser() == LotteryFlow.UserEnum.ROOMUSER.getId());
                lotteryUser.setUserId(lotteryFromDb.getUser().getId());
                lotteryUser.setCreateDate(DateUtil.toFormatDateString(lotteryFromDb.getCreateTime(), "HH:mm"));
                lotteryUser.setName(lotteryFromDb.getUser().getNickName());
                lotteryUser.setAvatar(lotteryFromDb.getUser().getAvatar());
                lotteryUser.setMe(userId.equals(lotteryUser.getUserId()));
                lotteryUsers.add(lotteryUser);
            }
        }
        lotteryResult.setLotteryUsers(lotteryUsers);

        return Result.success(lotteryResult);
    }

    @Resource
    private Redisson redisson;

    @Transactional
    public Result lottery(Long curUserId, String code) {
        logger.debugv("lottery start 中奖名单为：userid:{0},code:{1}", curUserId,code);
        Reward reward = rewardRepository.findByCode4Update(code);
        if (reward == null) {
            return Result.fail("红包不存在");
        }
        if (curUserId != null) {
            if (!curUserId.equals(reward.getUserId())) {
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
        logger.debugv("lottery start 中奖名单为：开始处理 {0}",code);
        List<Lottery> lotteries = new ArrayList<>();
        List<LotteryFlow> lotteryFlows = getReward(reward, lotteryList, lotteries);
        logger.debugv("lottery start 中奖名单为：{0} {1}", JSONObject.toJSONString(lotteryFlows),code);
        lotteryRepository.save(lotteries);
        lotteryFlowRepository.save(lotteryFlows);
        LotteryModel lotteryModel = factoryProvider.updateLotteryStatus(code);
        if (lotteryModel != null) {
            reward.setFactoryStatus(lotteryModel.getState());
        }
        //变更奖励状态
        reward.setStatus(END.getCode());
        if (curUserId != null) {
            //手动开启奖励的
            reward.setStartType(1);
        } else {
            //自动开启奖励的
            reward.setStartType(2);
        }
        rewardRepository.save(reward);
        //变更用余额
        for (int i = 0; i < lotteryFlows.size(); i++) {
            LotteryFlow lotteryFlow = lotteryFlows.get(i);
            Long lotteryUserId = lotteryFlow.getUser().getId();
            BigDecimal money = lotteryFlow.getMoney();
            String balanceFlowType = configService.getValueFromMap("balanceFlowType", "10");
            commonService.setBalance(lotteryUserId, money, LOTTERY, lotteryFlow.getId(), lotteryFlow.getId() + "", balanceFlowType);
        }
        return Result.success();
    }


    /****
     * 生成奖励人员
     * @param reward 奖励
     * @param lotteryList
     * @param lotteries
     * @return
     */
    @Transactional
    public List<LotteryFlow> getReward(Reward reward, List<Lottery> lotteryList, List<Lottery> lotteries) {
        BigDecimal money = reward.getMoney();
        int size = lotteryList.size();
        Map<Integer, BigDecimal> moneyMap = new HashMap<>();
        if (size == 1) {
            moneyMap.put(1, money);
        } else if (size == 2) {
            moneyMap.put(1, money.multiply(new BigDecimal(70)).divide(new BigDecimal(100)));
            moneyMap.put(2, money.multiply(new BigDecimal(30)).divide(new BigDecimal(100)));
        } else {
            moneyMap.put(1, money.multiply(new BigDecimal(60)).divide(new BigDecimal(100)));
            moneyMap.put(2, money.multiply(new BigDecimal(30)).divide(new BigDecimal(100)));
            moneyMap.put(3, money.multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
        }
        //获取随机中奖人数
        int index = size <= 3 ? size : 3;
        List<Lottery> lotteriesRandom = randomDataUtil.generateRandomPermutation(lotteryList, index);
        lotteries.clear();
        lotteries.addAll(lotteriesRandom);
        List<LotteryFlow> lotteryFlows = new ArrayList<>();

        List<String> remarkList = configService.getStringListConfig(ParamConstant.LOTTERY_REMARK);
        int count = remarkList.size();
        for (int i = 0; i < lotteries.size(); i++) {
            BigDecimal rewardMoney = moneyMap.get(i + 1);
            Lottery lottery = lotteries.get(i);
            lottery.setMoney(rewardMoney);
            LotteryFlow lotteryFlow = new LotteryFlow(lottery);
            Long userId = lottery.getUser().getId();
            User user = userRepository.findOne(userId);
            lotteryFlow.setUser(user);
            lotteryFlow.setDesc(remarkList.get(i % count));
            lotteryFlow.setRemarks("抢红包");
            lotteryFlows.add(lotteryFlow);
            LotteryFlow lotteryFlow1 = lotteryFlowRepository.findByReward(reward, userId);
            if(lotteryFlow1!=null){
                throw new ServiceException("服务器处理中");
            }

        }
        return lotteryFlows;
    }


    public Result<PageResult<LotteryFlowDTO>> history(Long userId, int page, int limit) {
        PageResult<LotteryFlow> lotteryFlows = lotteryFlowRepository.findByReward(userId, page, limit);
        return Result.success(lotteryFlows.result2Result(LotteryFlowDTO::new));
    }

    @Resource
    private ProductRepository productRepository;

    @Resource
    private FactoryProvider factoryProvider;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result<Reward> exitsReward(String code) {
        Reward reward = null;
        try {
            reward = rewardRepository.findByCode(code);
            if (reward != null) {
                return Result.success(reward);
            }
            long start = System.currentTimeMillis();
            LotteryModel lotteryModel = factoryProvider.findByLotteryModel(code);
            long end = System.currentTimeMillis();
            logger.debugv("" + ((end - start) / 1000l));
            if (lotteryModel == null) {
                return Result.fail("服务繁忙，请稍后再试");
            }
            int state = lotteryModel.getState();
            if (state == 2) {
                return Result.fail("兑奖码已兑奖完毕");
            } else if (state == 3) {
                return Result.fail("兑奖码不正确，请确认");
            } else if (state == 4) {
                return Result.fail("服务繁忙，请稍后再试");
            }
            ProductModel productInfo = factoryProvider.getProductInfo(code);
            Product product = null;
            if (productInfo != null && productInfo.getState() != 2 && productInfo.getState() != 3) {
                try {
                    product = productRepository.findByCode(code);
                    if(product == null){
                        product = productInfo.covert();
                        ProductType productType = productTypeRepository.findOrNew(productInfo.getName());
                        product.setProductType(productType);
                        product = productRepository.saveAndFlush(product);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                }
            }
            reward = new Reward();
            // 关联上商品信息
            reward.setProduct(product);
            BigDecimal intergal = lotteryModel.getIntergal();
            BigDecimal money = lotteryModel.getBouns();
            if (intergal.compareTo(new BigDecimal(0)) > 0) {
                money = intergal.divide(new BigDecimal(10));
            }
            reward.setMoney(money);
            reward.setCode(code);
            reward.setActivityId(1L);
            reward.setMd5Code(MD5Util.getMD5(code));
            reward.setPlanId(lotteryModel.getPlanId());
            reward = rewardRepository.saveAndFlush(reward);
        } catch (Exception e) {
            logger.debugv("数据获取失败");
        }
        return Result.success(reward);
    }


    public String forward(String code) {
        return concatUrl(LotteryConstantConfig.Lottery_QR_FORWARD_PATH, code);
    }


    public String concatUrl(String url, String code) {
        StringBuffer stringBuffer = new StringBuffer(url);
        if (url.contains("?")) {
            stringBuffer.append("&");
        } else {
            stringBuffer.append("?");
        }
        stringBuffer.append("barcode=");
        stringBuffer.append(code);
        return stringBuffer.toString();
    }

}
