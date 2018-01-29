package com.h9.admin.job;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.service.HotelService;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.utils.DateUtil;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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

    @Scheduled(cron = "0 0 0 * * *")
    public void scan() {

        logger.info("HotelOrderJob go ----------->");
        String formatDate = DateUtil.formatDate(new Date(), DateUtil.FormatType.NON_SEPARATOR_DAY);
        String lockKey = "h9:HotelOrderJob:lock:" + formatDate;

        if (!lock.getLock(lockKey)) {
            return;
        }
        List<HotelOrder> resultStream = hotelOrderRepository.findByOrderStatus(HotelOrder.OrderStatusEnum.NOT_PAID.getCode());
        resultStream.forEach(order -> {
            try {
                handleOrder(order);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        });

    }

    public void handleOrder(HotelOrder order) {
        BigDecimal payMoney4JiuYuan = order.getPayMoney4JiuYuan();
        BigDecimal payMoney4Wechat = order.getPayMoney4Wechat();

        logger.info("处理定订单id:" + order.getId());


        if (payMoney4Wechat.compareTo(new BigDecimal(0)) > 0) {

            logger.info("退 微信支付金额成功，酒店订单Id: " + order.getId() + payMoney4Wechat);
            Result result = hotelService.refundOrder(order.getId());
            logger.info("退款 结果： " + JSONObject.toJSONString(result));

            if(result.getCode() == 1){
                return;
            }
        }

        if (payMoney4JiuYuan.compareTo(new BigDecimal(0)) > 0) {
            //退余额
            commonService.setBalance(order.getUserId(), payMoney4JiuYuan,
                    BalanceFlow.BalanceFlowTypeEnum.REFUND.getId(),
                    order.getId(), "", BalanceFlow.BalanceFlowTypeEnum.REFUND.getName());
            logger.info("退酒元成功，酒店订单Id: " + order.getId() + " 金额：" + payMoney4JiuYuan);
        }

        order.setOrderStatus(HotelOrder.OrderStatusEnum.CANCEL.getCode());
        hotelOrderRepository.save(order);
    }
}
