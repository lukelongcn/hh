package com.h9.admin.job;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.service.HotelService;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.user.UserCount;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.UserCountRepository;
import com.h9.common.utils.DateUtil;
import com.mysql.jdbc.TimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.h9.common.db.entity.hotel.HotelOrder.OrderStatusEnum.WAIT_ENSURE;

/**
 * Created by itservice on 2018/1/18.
 */
@Component
public class HotelOrderJob {

    @Resource
    private HotelOrderRepository hotelOrderRepository;

    @Resource
    private CommonService commonService;

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private HotelService hotelService;
    @Resource
    private Lock lock;
    @Resource
    private UserCountRepository userCountRepository;

    @Resource
    private RedisBean redisBean;

    @Scheduled(cron = "0 0 0 * * *")
    public void scan() {

        logger.info("HotelOrderJob go ----------->");
        String formatDate = DateUtil.formatDate(new Date(), DateUtil.FormatType.NON_SEPARATOR_DAY);
        String lockKey = "h9:HotelOrderJob:lock:" + formatDate;

        if (!lock.getLock(lockKey)) {
            return;
        }

        calcUserCount();

        List<HotelOrder> resultStream = hotelOrderRepository.findByOrderStatus(
                HotelOrder.OrderStatusEnum.NOT_PAID.getCode()
                , WAIT_ENSURE.getCode());
        resultStream.forEach(order -> {
            try {
                handleOrder(order);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        });


    }

    private void calcUserCount() {
        //统计活跃人数
        Date date = DateUtil.getDate(new Date(), -1, Calendar.DAY_OF_YEAR);
        String dateKey = DateUtil.formatDate(date, DateUtil.FormatType.MONTH);
        String redisKey = RedisKey.getUserCountKey(date);

        List<UserCount> dayKeyList = userCountRepository.findByDayKey(dateKey);

        UserCount userCountEntity = null;
        if (CollectionUtils.isNotEmpty(dayKeyList)) {
            userCountEntity = dayKeyList.get(0);
        } else {
            userCountEntity = new UserCount();
        }

        Long userCount = redisBean.getStringTemplate()
                .execute((RedisCallback<Long>) connection ->
                        connection.bitCount(((RedisSerializer<String>) redisBean.getStringTemplate()
                                .getKeySerializer())
                                .serialize(redisKey)));


//        Long peopleNumbers = userCountEntity.getPeopleNumbers();
//        peopleNumbers = userCount;
        userCountEntity.setPeopleNumbers(userCount);
        userCountEntity.setDayKey(dateKey);
        userCountRepository.save(userCountEntity);
        logger.info("userCount: " + userCount);
    }

    public void handleOrder(HotelOrder order) {
        BigDecimal payMoney4JiuYuan = order.getPayMoney4JiuYuan();
        BigDecimal payMoney4Wechat = order.getPayMoney4Wechat();

        logger.info("处理定订单id:" + order.getId());

        order.setOrderStatus(HotelOrder.OrderStatusEnum.CANCEL.getCode());

        if (payMoney4Wechat.compareTo(new BigDecimal(0)) > 0) {

            logger.info("退 微信支付金额成功，酒店订单Id: " + order.getId() +"，金额："+ payMoney4Wechat);
            Result result = hotelService.refundOrder(order.getId());
            logger.info("退款 结果： " + JSONObject.toJSONString(result));

            if (result.getCode() == 1) {
                return;
            }
            order.setOrderStatus(HotelOrder.OrderStatusEnum.REFUND_MONEY.getCode());
        }

        if (payMoney4JiuYuan.compareTo(new BigDecimal(0)) > 0) {
            //退余额
            commonService.setBalance(order.getUserId(), payMoney4JiuYuan,
                    BalanceFlow.BalanceFlowTypeEnum.REFUND.getId(),
                    order.getId(), "", BalanceFlow.BalanceFlowTypeEnum.REFUND.getName());
            logger.info("退酒元成功，酒店订单Id: " + order.getId() + " 金额：" + payMoney4JiuYuan);
            order.setOrderStatus(HotelOrder.OrderStatusEnum.REFUND_MONEY.getCode());
        }

        hotelOrderRepository.save(order);
    }
}
