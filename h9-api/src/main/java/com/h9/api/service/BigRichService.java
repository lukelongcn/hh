package com.h9.api.service;

import com.h9.api.model.vo.BigRichRecordVO;
import com.h9.api.model.vo.BigRichVO;
import com.h9.api.model.vo.UserBigRichRecordVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.bigrich.OrdersLotteryActivity;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.OrdersLotteryActivityRepository;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.bigrich.OrdersLotteryActivity.statusEnum.ENABLE;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
@Service
public class BigRichService {

    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private OrdersLotteryActivityRepository ordersLotteryActivityRepository;
    @Resource
    private OrdersRepository ordersRepository;

    public Result getRecord(long userId, Integer page, Integer limit) {
        BigRichVO bigRichVO = new BigRichVO();
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        User user = userRepository.findOne(userId);
        PageResult<OrdersLotteryActivity> pageResult = ordersLotteryActivityRepository.findAllDetail(page, limit);
        if (pageResult != null) {
            PageResult<BigRichRecordVO> pageResultRecord = pageResult.result2Result(this::activityToRecord);
            bigRichVO.setRecordList(pageResultRecord);
        }
        bigRichVO.setBigRichMoney(MoneyUtils.formatMoney(userAccount.getBigRichMoney()));

        List<Orders> ordersList = ordersRepository.findByUserAndOrdersLotteryId(userId);

        List<Long> collect = ordersList.stream()
                .map(orders -> orders.getOrdersLotteryId())
                .distinct()
                .filter(id -> {
                    OrdersLotteryActivity ordersLotteryActivity = ordersLotteryActivityRepository.findOne(id);
                    int status = ordersLotteryActivity.getStatus();
                    return status == ENABLE.getCode();
                })
                .collect(Collectors.toList());

        bigRichVO.setLotteryChance(collect.size());

        return Result.success(bigRichVO);
    }


    @Transactional(rollbackFor = Exception.class)
    public BigRichRecordVO activityToRecord(OrdersLotteryActivity e) {
        // 创建记录对象
        BigRichRecordVO bigRichRecordVO = new BigRichRecordVO();
        if (e.getWinnerUserId() == null) {
            return bigRichRecordVO;
        }

        if (e.getWinnerUserId() == null) {
            bigRichRecordVO.setUserName("");
            bigRichRecordVO.setLotteryMoney("199.00");
        } else {
            User user = userRepository.findOne(e.getWinnerUserId());
            bigRichRecordVO.setUserName(user.getNickName());
            bigRichRecordVO.setLotteryMoney(MoneyUtils.formatMoney(e.getMoney()));
        }


        bigRichRecordVO.setStartLotteryTime(DateUtil.formatDate(e.getStartLotteryTime(), DateUtil.FormatType.DOT_MINUTE));
        bigRichRecordVO.setId(e.getId());

        return bigRichRecordVO;
    }


    public Result getUserRecord(long userId, Integer page, Integer limit) {
        PageResult<Orders> pageResult = ordersRepository.findByUserId(userId, page, limit);
        if (pageResult == null) {
            return Result.fail("暂无记录");
        }
        PageResult<UserBigRichRecordVO> pageResultRecord = pageResult.result2Result(this::activityToUserRecord);
        return Result.success(pageResultRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserBigRichRecordVO activityToUserRecord(Orders e) {
        UserBigRichRecordVO userBigRichRecordVO = new UserBigRichRecordVO();

        OrdersLotteryActivity ordersLotteryActivity = ordersLotteryActivityRepository.findOneById(e.getOrdersLotteryId());
        if (ordersLotteryActivity == null) {
            return userBigRichRecordVO;
        }
        // 订单id
        userBigRichRecordVO.setOrdersId(e.getId());
        // 抽奖时间
        userBigRichRecordVO.setStartLotteryTime(DateUtil.formatDate(ordersLotteryActivity.getStartLotteryTime(), DateUtil.FormatType.MINUTE));
        // 期数
        userBigRichRecordVO.setNumber(ordersLotteryActivity.getNumber());
        // 状态
        if (e.getUser().getId().equals(ordersLotteryActivity.getWinnerUserId())
                && ordersLotteryActivity.getStartLotteryTime().before(new Date())) {
            // 已中奖
            userBigRichRecordVO.setStatus(1);
        }
        // 待开奖
        else if (ordersLotteryActivity.getStartLotteryTime().after(new Date())) {
            userBigRichRecordVO.setStatus(2);
        }
        // 未中奖
        else {
            userBigRichRecordVO.setStatus(3);
        }

        // 获得方式
        userBigRichRecordVO.setWay("兑换商品");
        // 金额
        BigDecimal bigDecimal = ordersLotteryActivity.getMoney().setScale(2, BigDecimal.ROUND_DOWN);
        userBigRichRecordVO.setMoney(bigDecimal);
        return userBigRichRecordVO;
    }

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 参与大富贵活动
     *
     * @param orders 参与的 订单
     * @return
     */
//    @SuppressWarnings("Duplicates")
//    public boolean joinBigRich(Orders orders) {
//        int orderFrom = orders.getOrderFrom();
//        if (orderFrom == 2) {
//            return false;
//        }
//        Date createTime = orders.getCreateTime();
//        User user = userRepository.findOne(orders.getUser().getId());
//        OrdersLotteryActivity lotteryTime = ordersLotteryActivityRepository.findAllTime(createTime);
//        if (lotteryTime != null) {
//            orders.setOrdersLotteryId(lotteryTime.getId());
//            Map<Long, Integer> map = user.getLotteryChance();
//            boolean containsKey = map.containsKey(lotteryTime.getId());
//            if (containsKey) {
//                Integer count = map.get(lotteryTime.getId());
//                count++;
//                map.put(lotteryTime.getId(), count);
//            }else{
//                map.put(lotteryTime.getId(), 1);
//            }
//            user.setLotteryChance(map);
////            user.setLotteryChance(user.getLotteryChance() + 1);
//            lotteryTime.setJoinCount(lotteryTime.getJoinCount() + 1);
//            logger.info("订单号 " + orders.getId() + " 参与大富贵活动成功 活动id " + lotteryTime.getId());
//            ordersRepository.saveAndFlush(orders);
//            userRepository.save(user);
//            ordersLotteryActivityRepository.save(lotteryTime);
//            ordersRepository.saveAndFlush(orders);
//            return true;
//        } else {
//            return false;
//        }
//    }


}
