package com.h9.api.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.PayNotifyVO;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.PayInfoRepository;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static com.h9.common.db.entity.hotel.HotelOrder.OrderStatusEnum.WAIT_ENSURE;

/**
 * Created by itservice on 2018/1/17.
 */
@Component
public class HotelPayHandler extends AbPayHandler{
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PayInfoRepository payInfoRepository;
    @Resource
    private HotelOrderRepository hotelOrderRepository;
    @Resource
    private CommonService commonService;
    @Override
    public boolean callback(PayNotifyVO payNotifyVO, PayInfo payInfo) {
        logger.info("酒店支付回调: payNotifyVO : "+ JSONObject.toJSONString(payNotifyVO)
                +" payInfo: "+JSONObject.toJSONString(payInfo));
        Integer status = payInfo.getStatus();
        if(status  == 2){
            return true;
        }
        payInfo.setStatus(2);
        payInfoRepository.save(payInfo);

        Long orderId = payInfo.getOrderId();
        HotelOrder hotelOrder = hotelOrderRepository.findOne(orderId);
        BigDecimal payMoney4Wechat = payInfo.getMoney();
        payMoney4Wechat = payMoney4Wechat.add(payInfo.getMoney());

        BigDecimal paidMoney = hotelOrder.getPayMoney4Wechat();
        payMoney4Wechat = payMoney4Wechat.add(paidMoney);
        hotelOrder.setPayMoney4Wechat(payMoney4Wechat);

        BigDecimal payMoney4JiuYuan = hotelOrder.getPayMoney4JiuYuan();

        //记录两条流水

        commonService.setBalance(hotelOrder.getUserId(), payInfo.getMoney().abs().negate(),
                BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getId(), hotelOrder.getId(), "",
                BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getName());

        commonService.setBalance(hotelOrder.getUserId(), payInfo.getMoney().abs(),
                BalanceFlow.BalanceFlowTypeEnum.Recharge.getId(), hotelOrder.getId(), "",
                BalanceFlow.BalanceFlowTypeEnum.Recharge.getName());



        BigDecimal totalPaidMoney = payMoney4JiuYuan.add(payMoney4Wechat);
        //金额 大于 等于 总金额，订单状态变成 WAIT_ENSURE(2,"待确认")
        if(totalPaidMoney.compareTo(hotelOrder.getTotalMoney()) >= 0){
            hotelOrder.setOrderStatus(HotelOrder.OrderStatusEnum.WAIT_ENSURE.getCode());
        }
        hotelOrderRepository.save(hotelOrder);
        return true;
    }
}
