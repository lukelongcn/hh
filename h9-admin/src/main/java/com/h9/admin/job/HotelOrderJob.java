package com.h9.admin.job;

import com.h9.common.common.CommonService;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.repo.HotelOrderRepository;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Scheduled(cron = "0 0 0 * * *")
    public void scan(){

        logger.info("HotelOrderJob go ----------->");
        try (Stream<HotelOrder> resultStream = hotelOrderRepository.findByOrderStatus(HotelOrder.OrderStatusEnum.NOT_PAID.getCode())) {
            resultStream.forEach(order -> {
                BigDecimal payMoney4JiuYuan = order.getPayMoney4JiuYuan();
                BigDecimal payMoney4Wechat = order.getPayMoney4Wechat();

                if (payMoney4JiuYuan.compareTo(new BigDecimal(0)) > 0) {
                    //退余额
                    commonService.setBalance(order.getUserId(), payMoney4JiuYuan,
                            BalanceFlow.BalanceFlowTypeEnum.REFUND.getId(),
                            order.getId(), "", BalanceFlow.BalanceFlowTypeEnum.REFUND.getName());
                    order.setOrderStatus(HotelOrder.OrderStatusEnum.CANCEL.getCode());
                    logger.info("退款酒店订单Id: "+order.getId()+" 金额："+payMoney4JiuYuan);
                    hotelOrderRepository.save(order);
                }

                if (payMoney4Wechat.compareTo(new BigDecimal(0)) > 0) {
                    //TODO 调用退款接口
                }
            });
        }
    }
}
