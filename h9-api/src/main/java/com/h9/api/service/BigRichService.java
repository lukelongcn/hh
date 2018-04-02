package com.h9.api.service;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.vo.BigRichRecordVO;
import com.h9.api.model.vo.BigRichVO;
import com.h9.api.model.vo.UserBigRichRecordVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.OrdersLotteryActivityRepository;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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


    private Boolean chance = false;

    public Result getRecord(long userId, Integer page, Integer limit) {
        BigRichVO bigRichVO = new BigRichVO();
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        User user = userRepository.findOne(userId);
        PageResult<OrdersLotteryActivity> pageResult = ordersLotteryActivityRepository.findAllDetail(page, limit);
        if (pageResult != null) {
            PageResult<BigRichRecordVO> pageResultRecord = pageResult.result2Result(this::activityToRecord);
            bigRichVO.setRecordList(pageResultRecord);
        }
        bigRichVO.setBigRichMoney(userAccount.getBigRichMoney());
        if (user.getLotteryChance() == 1) {
            bigRichVO.setLotteryChance(1);
        }
        return Result.success(bigRichVO);
    }


    @Transactional(rollbackFor = Exception.class)
    public BigRichRecordVO activityToRecord(OrdersLotteryActivity e) {
        // 创建记录对象
        BigRichRecordVO bigRichRecordVO = new BigRichRecordVO();
        if (e.getWinnerUserId() == null) {
            return null;
        }
        User user = userRepository.findOne(e.getWinnerUserId());
        if (user == null || e.getMoney() == null) {
            return bigRichRecordVO;
        }
        bigRichRecordVO.setUserName(user.getNickName());
        bigRichRecordVO.setLotteryMoney(e.getMoney());
        bigRichRecordVO.setStartLotteryTime(DateUtil.formatDate(e.getStartLotteryTime(), DateUtil.FormatType.MINUTE));
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
            return null;
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

    /**
     * 通过订单号参加大富贵活动
     * @param orders
     * @return
     */
    @Transactional
    public Orders joinBigRich(Orders orders) {
        Date createTime = orders.getCreateTime();
        List<OrdersLotteryActivity> lotteryTime = ordersLotteryActivityRepository.findAllTime(createTime);
        lotteryTime.forEach(o -> {
            List<Orders> list = ordersRepository.findUserfulOrders(o.getStartTime(),o.getEndTime(),orders.getUser().getId());
            list.forEach(order ->{
                if (order.getOrdersLotteryId() != null){
                    return;
                }
            });
            orders.setOrdersLotteryId(o.getId());
        });
        return orders;
    }
}
