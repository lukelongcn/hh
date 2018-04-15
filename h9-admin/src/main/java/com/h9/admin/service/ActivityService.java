package com.h9.admin.service;

import com.h9.admin.model.dto.AddBigRichDTO;
import com.h9.admin.model.dto.AddWinnerUserDTO;
import com.h9.admin.model.vo.BigRichListVO;
import com.h9.admin.model.vo.JoinBigRichUser;
import com.h9.admin.model.vo.LotteryFlowActivityVO;
import com.h9.common.common.CommonService;
import com.h9.common.common.MailService;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.bigrich.OrdersLotteryRelation;
import com.h9.common.db.entity.lottery.Lottery;
import com.h9.common.db.entity.bigrich.OrdersLotteryActivity;
import com.h9.common.db.entity.lottery.WinnerOptRecord;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import com.h9.common.modle.dto.RewardQueryDTO;
import com.h9.admin.model.vo.RewardVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.lottery.Reward;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MobileUtils;
import com.h9.common.utils.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.h9.common.db.entity.bigrich.OrdersLotteryActivity.statusEnum.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: George
 * @date: 2017/11/7 19:40
 */
@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LotteryFlowRepository lotteryFlowRepository;
    @Autowired
    private LotteryRepository lotteryRepository;
    @Autowired
    private OrdersLotteryActivityRep ordersLotteryActivityRep;
    @Resource
    private UserService userService;
    @Resource
    private WinnerOptRecordRep winnerOptRecordRep;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private MailService mailService;
    @Resource
    private CommonService commonService;
    @Resource
    private TransactionalService transactionalService;
    @Resource
    private UserAccountRepository userAccountRepository;


    public Result<PageResult<RewardVO>> getRewards(RewardQueryDTO rewardQueryDTO) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = this.rewardRepository.pageRequest(rewardQueryDTO.getPageNumber(), rewardQueryDTO.getPageSize(), sort);
        Page<Reward> rewards = this.rewardRepository.findAllReward(rewardQueryDTO, pageRequest);
        PageResult<Reward> pageResult = new PageResult<>(rewards);
        return Result.success(this.toRewardVO(pageResult));
    }

    private PageResult<RewardVO> toRewardVO(PageResult rewards) {
        List<RewardVO> rewardVOList = new ArrayList<>();
        for (Reward reward : (List<Reward>) rewards.getData()) {
            rewardVOList.add(RewardVO.toRewardVO(reward));
        }
        rewards.setData(rewardVOList);
        return rewards;
    }

    public Result<PageResult<LotteryFlowActivityVO>> getLotteryFlows(LotteryFlowActivityDTO lotteryFlowActivityDTO) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<Lottery> lotteryFlows = this.lotteryRepository.findAll(this.lotteryRepository
                .buildActivitySpecification(lotteryFlowActivityDTO), lotteryFlowActivityDTO.toPageRequest(sort));
        PageResult<Lottery> pageResult = new PageResult<>(lotteryFlows);
        return Result.success(pageResult.result2Result(LotteryFlowActivityVO::new));
    }


    /**
     * 新增/编辑 大富贵期数
     *
     * @param addBigRichDTO id 为空时代表 新增，不为空代表 修改
     * @return
     */
    @Transactional
    public Result editBigRichActivity(AddBigRichDTO addBigRichDTO) {
        Date endTime = new Date(addBigRichDTO.getEndTime());
        Date startTime = new Date(addBigRichDTO.getStartTime());
        Date startLotteryTime = new Date(addBigRichDTO.getStartLotteryTime());

        if (startLotteryTime.getTime() < startTime.getTime()) {
            return Result.fail("请填写正确的开奖时间");
        }

        if (endTime.getTime() < startTime.getTime()) {
            return Result.fail("请填写正确 开始-到结束 时间");
        }

        if (endTime.before(new Date())) {
            return Result.fail("不可以新增过去时间的期数");
        }

        if (endTime.getTime() == startTime.getTime()) {
            return Result.fail("请选择正确的开始时间和结束时间");
        }

        if (startLotteryTime.getTime() < endTime.getTime()) {
            return Result.fail("开奖时间必需等于结束时间或之后");
        }


        List<OrdersLotteryActivity> byDate1 = ordersLotteryActivityRep.findByDateId(startTime, addBigRichDTO.getId());
        List<OrdersLotteryActivity> byDate2 = ordersLotteryActivityRep.findByDateId(endTime, addBigRichDTO.getId());

        List<OrdersLotteryActivity> byDate3 = ordersLotteryActivityRep.findByDateId2(startTime, endTime, addBigRichDTO.getId());
        List<OrdersLotteryActivity> byDate4 = ordersLotteryActivityRep.findByDateId3(startTime, endTime, addBigRichDTO.getId());

        if (CollectionUtils.isNotEmpty(byDate1) || CollectionUtils.isNotEmpty(byDate2)
                || CollectionUtils.isNotEmpty(byDate3)
                || CollectionUtils.isNotEmpty(byDate4)) {
            return Result.fail("设置的活动区间不能与已有的时间区间重复");
        }
        OrdersLotteryActivity ordersLotteryActivity = null;

        if (addBigRichDTO.getId() == -1) {
            //新增
            ordersLotteryActivity = getOrdersLotteryActivity(addBigRichDTO, null);
        } else {
            //修改
            OrdersLotteryActivity activity = ordersLotteryActivityRep.findOne(addBigRichDTO.getId());
            if (activity == null) {
                return Result.fail("期号不存在");
            }
            if (activity.getStatus() == BAN.getCode()) {
                ordersLotteryActivity = getOrdersLotteryActivity(addBigRichDTO, activity);
                //调整参与人数
                changeBigRichJoinUser(activity, addBigRichDTO);
                ordersLotteryActivityRep.save(ordersLotteryActivity);
                return Result.success();
            } else {
                return Result.fail("只有在禁用状态可以编辑");
            }
        }

        ordersLotteryActivityRep.save(ordersLotteryActivity);
        return Result.success();
    }

    /**
     * 改变 参与活动的用户
     *
     * @param activity
     * @param addBigRichDTO
     */
    private void changeBigRichJoinUser(OrdersLotteryActivity activity, AddBigRichDTO addBigRichDTO) {
        boolean timeDurationChange1 = activity.getStartTime().getTime() == activity.getEndTime().getTime();
        boolean timeDurationChange2 = activity.getStartTime().getTime() == activity.getEndTime().getTime();

        if (timeDurationChange1 && timeDurationChange2) {
            Date start = new Date(addBigRichDTO.getStartTime());
            Date endDate = new Date(addBigRichDTO.getEndTime());
            int i1 = ordersRepository.updateOrdersLotteryId(activity.getId());
            logger.info("更新　" + i1 + " 条记录");
            int i2 = ordersRepository.updateByDateAndStatus(start, endDate, 2, activity.getId());
            logger.info("更新　" + i2 + " 条记录");
        }
    }


    /**
     * 验证是否可以修改
     *
     * @param addBigRichDTO         要修改的内容
     * @param ordersLotteryActivity 修改的活动
     * @return
     */
    private Result bigRichCanEdit(AddBigRichDTO addBigRichDTO, OrdersLotteryActivity ordersLotteryActivity) {
        return null;
    }


    /**
     * @param addBigRichDTO
     * @param activity
     * @return
     */
    public OrdersLotteryActivity getOrdersLotteryActivity(AddBigRichDTO addBigRichDTO, OrdersLotteryActivity activity) {
        OrdersLotteryActivity ordersLotteryActivity = null;

        if (activity == null) {
            ordersLotteryActivity = new OrdersLotteryActivity();
        } else {
            ordersLotteryActivity = ordersLotteryActivityRep.findOne(activity.getId());
        }

        ordersLotteryActivity.setStartTime(new Date(addBigRichDTO.getStartTime()));
        ordersLotteryActivity.setEndTime(new Date(addBigRichDTO.getEndTime()));
        Long startLotteryTime = addBigRichDTO.getStartLotteryTime();
        String number = DateUtil.formatDate(new Date(startLotteryTime), DateUtil.FormatType.NON_SEPARATOR_DAY);
        ordersLotteryActivity.setNumber(number);
        ordersLotteryActivity.setStatus(addBigRichDTO.getStatus());
        ordersLotteryActivity.setStartLotteryTime(new Date(startLotteryTime));

        return ordersLotteryActivity;
    }

    /**
     * 大富贵活动期数列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Result bigRichList(Integer pageNumber, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = ordersLotteryActivityRep.pageRequest(pageNumber, pageSize, sort);
//        Page<OrdersLotteryActivity> page = ordersLotteryActivityRep.findByStatus(1, pageRequest);
        Page<OrdersLotteryActivity> page = ordersLotteryActivityRep.findAll(pageRequest);

        PageResult<BigRichListVO> mapVO = new PageResult<>(page).map(activity -> {
            Long winnerUserId = activity.getWinnerUserId();
            User user = null;
            if (winnerUserId != null) {
                user = userRepository.findOne(winnerUserId);
            }
            long joinCount = (long) ordersRepository.findByCount(activity.getId());
            return new BigRichListVO(activity, user, joinCount);
        });

        return Result.success(mapVO);
    }

    /**
     * 获得map中的第的元素
     *
     * @param userMap
     * @return
     */
    public User getFromMap(Map<Long, BigDecimal> userMap) {

        if (userMap == null) return null;
        Set<Long> keySet = userMap.keySet();
        for (Long id : keySet) {
            User user = userRepository.findOne(id);
            if (user != null) {
                return user;
            }
        }
        return null;
    }

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 添加中奖用户
     *
     * @param addWinnerUserDTO
     * @param userId
     * @return
     */
    public Result addWinnerUser(AddWinnerUserDTO addWinnerUserDTO, Long userId) {

        String phone = addWinnerUserDTO.getPhone();
        Long activityId = addWinnerUserDTO.getActivityId();

        if (!MobileUtils.isMobileNO(phone)) return Result.fail("请填写正确的手机号码");
        User user = userRepository.findByPhone(phone);

        if (user == null) {
            user = userService.registerByPhone(phone);
        }

        OrdersLotteryActivity activity = ordersLotteryActivityRep.findOne(activityId);
        if (activity == null) {
            logger.info("id 为 " + activityId + " 的期数不存在");
            return Result.fail("期数不存在");
        }


        BigDecimal money = addWinnerUserDTO.getMoney();
        if (money.longValue() >= 1 && money.longValue() < 99999.99) {
            activity.setMoney(money);
            activity.setWinnerUserId(user.getId());
            ordersLotteryActivityRep.saveAndFlush(activity);
            //记录添加 中奖人 操作日志
            WinnerOptRecord winnerOptRecord = new WinnerOptRecord(null, activity.getId(), user.getId(), userId);
            winnerOptRecordRep.save(winnerOptRecord);

            return Result.success();
        } else {
            return Result.fail("请输入正确的金额,1-99999.99");
        }

    }

    @Resource
    private OrdersLotteryRelationRep ordersLotteryRelationRep;
    /**
     * 参与用户列表
     *
     * @param id
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public Result bigRichUsers(Long id, int pageSize, int pageNumber) {
        OrdersLotteryActivity ordersLotteryActivity = ordersLotteryActivityRep.findOne(id);
        if (ordersLotteryActivity == null) {
            return Result.fail("期数不存在");
        }

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = ordersRepository.pageRequest(pageNumber, pageSize, sort);
        Page<Orders> page = ordersRepository.findByordersLotteryId(id, pageRequest);
        Page<OrdersLotteryRelation> ordersLotteryRelationPage = ordersLotteryRelationRep
                .findByOrdersLotteryActivityId(ordersLotteryActivity.getId(), pageRequest);

        List tempList = new ArrayList();
        AtomicReference<Long> index = new AtomicReference<>(1L);
        PageResult<JoinBigRichUser> mapVo = new PageResult<>(ordersLotteryRelationPage).map(el -> {

            Long userId = el.getUserId();
            User user = userRepository.findOne(userId);
            String money = null;
            Long winnerUserId = ordersLotteryActivity.getWinnerUserId();
            if (user.getId().equals(winnerUserId)) {
                if (tempList.size() <= 1) {
                    money = MoneyUtils.formatMoney(ordersLotteryActivity.getMoney());
                    tempList.add(new Object());
                }
            }

            JoinBigRichUser joinBigRichUser = new JoinBigRichUser(index.get(),
                    user.getPhone(), user.getNickName(), money, ordersLotteryActivity.getNumber(), el.getOrderId() + "");
            index.getAndSet(index.get() + 1+((pageNumber-1)*pageSize));
            return joinBigRichUser;
        });
        return Result.success(mapVo);
    }

    /**
     * 大富贵活动的抽奖
     */
    @Transactional
    public void startBigRichLottery() {
        logger.info("startBigRichLottery");
        Date now = new Date();
        Date willDate = DateUtil.getDate(now, 5, Calendar.MINUTE);
        List<OrdersLotteryActivity> lotteryActivityList = ordersLotteryActivityRep.findByLotteryDate(willDate);
        if (CollectionUtils.isEmpty(lotteryActivityList)) {
            logger.info("要处理的任务数 为空");
            return;
        }
        logger.info("要处理的任务数：" + lotteryActivityList.size());

        for (OrdersLotteryActivity ordersLotteryActivity : lotteryActivityList) {
            Date startLotteryTime = ordersLotteryActivity.getStartLotteryTime();
            long millisecond = startLotteryTime.getTime() - new Date().getTime();
            sleepTaskStartLottery(millisecond, ordersLotteryActivity);
        }

    }


    /**
     * 异步调用开奖任务
     *
     * @param millisecond           暂停时间
     * @param ordersLotteryActivity
     */
    @Async
    @Transactional
    public void sleepTaskStartLottery(long millisecond, OrdersLotteryActivity ordersLotteryActivity) {
        try {
            if (millisecond < 0) {
                millisecond = 0;
            }
            logger.info("sleep " + millisecond + "毫秒");
            Thread.sleep(millisecond);
        } catch (Exception e) {
            logger.info("开奖失败啦!", e);
        }
        // 开奖
        ordersLotteryActivity = transactionalService.findOneNewTrans(ordersLotteryActivityRep, ordersLotteryActivity.getId());
        if (ordersLotteryActivity.getStatus() != ENABLE.getCode()) {
            logger.info("大富贵活动Id " + ordersLotteryActivity.getId() + " 已开奖");
            return;
        }
        handlerLotteryResultUser(ordersLotteryActivity);

        ordersLotteryActivity.setStatus(OrdersLotteryActivity.statusEnum.FINISH.getCode());
        ordersLotteryActivityRep.save(ordersLotteryActivity);
    }

    /**
     * 处理抽奖中奖用户
     *
     * @param ordersLotteryActivity
     * @return
     */
    private Result handlerLotteryResultUser(OrdersLotteryActivity ordersLotteryActivity) {

        Long winnerUserId = ordersLotteryActivity.getWinnerUserId();
        if (winnerUserId == null) {
            logger.info("期号id :" + ordersLotteryActivity.getId() + " 中奖用户不存在 userId: " + winnerUserId);
            return null;
        }

        User user = userRepository.findOne(winnerUserId);
        BigDecimal money = ordersLotteryActivity.getMoney();
        if (user != null) {

            commonService.setBalance(winnerUserId, money,
                    BalanceFlow.BalanceFlowTypeEnum.BIG_RICH_BONUS.getId(),
                    ordersLotteryActivity.getId(), "",
                    BalanceFlow.BalanceFlowTypeEnum.BIG_RICH_BONUS.getName());

            UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
            BigDecimal bigRichMoney = userAccount.getBigRichMoney();
            if (bigRichMoney == null) bigRichMoney = BigDecimal.ZERO;
            userAccount.setBigRichMoney(bigRichMoney.add(money));
            userAccountRepository.save(userAccount);

        } else {
            logger.info("用户不存在 id: " + winnerUserId);
        }

        return null;
    }

    /**
     * 修改状态
     *
     * @param id     id
     * @param status 1 启用，0 禁用
     * @return
     */
    public Result<JoinBigRichUser> modifyStatus(Long id, Integer status) {
        OrdersLotteryActivity activity = ordersLotteryActivityRep.findOne(id);
        if (activity == null) {
            return Result.fail("期数不存在");
        }

        if (status != 1 && status != 0) {
            return Result.fail("不能修改此活动的状态");
        }

        if (activity.getStatus() == FINISH.getCode()) {
            return Result.fail("活动已结束");
        }


        activity.setStatus(status == 1 ? ENABLE.getCode() : BAN.getCode());

        ordersLotteryActivityRep.save(activity);
        return Result.success();
    }


    @Resource
    private OrderService orderService;

    @Transactional
    public void method2() {
        logger.info("method2");
        activityRepository.findOne(1L);
        orderService.method1();
    }

    public Result startBigRIchLotteryTest(Long id) {

        OrdersLotteryActivity ordersLotteryActivity = ordersLotteryActivityRep.findOne(id);

        if (ordersLotteryActivity != null) {
            sleepTaskStartLottery(1L, ordersLotteryActivity);
            return Result.success();
        }
        return Result.fail();
    }
}
