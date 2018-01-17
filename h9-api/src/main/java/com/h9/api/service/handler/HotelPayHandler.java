package com.h9.api.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.PayNotifyVO;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.PayInfoRepository;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
        hotelOrder.setOrderStatus(WAIT_ENSURE.getCode());

        hotelOrderRepository.save(hotelOrder);

        return true;
    }
}
