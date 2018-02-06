package com.h9.api.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.PayNotifyVO;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.PayInfoRepository;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/17.
 */
@Component
public class StorePayHandler extends AbPayHandler{
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PayInfoRepository payInfoRepository;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private HotelOrderRepository hotelOrderRepository;
    @Resource
    private CommonService commonService;
    @Override
    public boolean callback(PayNotifyVO payNotifyVO, PayInfo payInfo) {
        logger.info("商城支付callBack: payNotifyVO : "+ JSONObject.toJSONString(payNotifyVO)
                +" payInfo: "+JSONObject.toJSONString(payInfo));
        Integer status = payInfo.getStatus();
        if(status  == 2){
            return true;
        }
        payInfo.setStatus(2);
        payInfoRepository.save(payInfo);

        Long orderId = payInfo.getOrderId();
        Orders orders = ordersRepository.findOne(orderId);
        BigDecimal payMoney4Wechat = payInfo.getMoney();
//        payMoney4Wechat = payMoney4Wechat.add(payInfo.getMoney());
        orders.setStatus(Orders.statusEnum.WAIT_SEND.getCode());
        orders.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
        ordersRepository.save(orders);

        //记录两条流水
        commonService.setBalance(orders.getUser().getId(), payInfo.getMoney().abs(),
                BalanceFlow.BalanceFlowTypeEnum.Recharge.getId(), orderId, "",
                BalanceFlow.BalanceFlowTypeEnum.Recharge.getName());

        commonService.setBalance(orders.getUser().getId(), payInfo.getMoney().abs().negate(),
                BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getId(), orderId, "",
                BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getName());

        return true;
    }
}
