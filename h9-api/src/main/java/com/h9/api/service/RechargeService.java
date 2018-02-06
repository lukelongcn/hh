package com.h9.api.service;

import com.h9.api.model.dto.OrderDTO;
import com.h9.api.model.dto.PayNotifyVO;
import com.h9.api.model.dto.RechargeOrderVO;
import com.h9.api.model.vo.OrderVo;
import com.h9.api.model.vo.PayResultVO;
import com.h9.api.model.vo.PayVO;
import com.h9.api.provider.PayProvider;
import com.h9.api.service.handler.AbPayHandler;
import com.h9.api.service.handler.PayHandlerFactory;
import com.h9.common.base.Result;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.RechargeOrder;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.PayInfoRepository;
import com.h9.common.db.repo.RechargeOrderRepository;
import com.h9.common.db.repo.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.h9.api.model.dto.OrderDTO.PayMethodEnum.WX;
import static com.h9.api.model.dto.OrderDTO.PayMethodEnum.WXJS;

/**
 * Created with IntelliJ IDEA.
 *
 * RechargeService:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 17:53
 */
@Component
public class RechargeService {

    Logger logger = Logger.getLogger(RechargeService.class);

    @Resource
    private RechargeOrderRepository rechargeOrderRepository;

    @Resource
    private PayInfoRepository payInfoRepository;

    @Resource
    private PayProvider payProvider;

    @Resource
    private UserRepository userRepository;

    @Transactional
    public Result appRecharge(Long userId, BigDecimal money) {
        Result<PayVO> recharge = recharge(userId, money,true);
        if(!recharge.isSuccess()){
            return recharge;
        }
        PayVO data = recharge.getData();
        Long payOrderId = data.getPayOrderId();
        //APP 支付
        Result prepayResult = payProvider.getPrepayInfo(payOrderId);
        return Result.success(prepayResult.getData());
    }



    @Transactional
    public Result recharge(Long userId, BigDecimal money,boolean isApp) {
        if (money == null) {
            return Result.fail("请填入要充值的金额");
        }
        if (money.compareTo(new BigDecimal(0)) <= 0.001) {
            return Result.fail("请填入正确的充值金额");
        }

        if (money.compareTo(new BigDecimal(99999999)) > 0) {
            return Result.fail("充值金额太大暂不支持");
        }

        RechargeOrder rechargeOrder = new RechargeOrder();
        rechargeOrder.setUser_id(userId);
        rechargeOrder.setMoney(money);
        rechargeOrder = rechargeOrderRepository.saveAndFlush(rechargeOrder);


        PayInfo payInfo = new PayInfo();
        payInfo.setMoney(rechargeOrder.getMoney());
        payInfo.setOrderId(rechargeOrder.getId());
        payInfo.setOrderType(PayInfo.OrderTypeEnum.Recharge.getId());
        payInfo = payInfoRepository.save(payInfo);

        User user = userRepository.findOne(userId);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBusinessOrderId(payInfo.getId());
        orderDTO.setTotalAmount(money);
        orderDTO.setOpenId(user.getOpenId());
        orderDTO.setPayMethod(isApp? WX.getKey():WXJS.getKey());
        logger.debugv("开始支付");
        Result<OrderVo> result = payProvider.initOrder(orderDTO);
        if (!result.isSuccess()) {
            return result;
        }

        OrderVo orderVo = result.getData();
        String pay = payProvider.goPay(orderVo.getPayOrderId(), payInfo.getId());
        PayVO payVO = new PayVO();
        payVO.setPayOrderId(orderVo.getPayOrderId());
        payVO.setPayUrl(pay);
        payVO.setOrderId(rechargeOrder.getId());
        return Result.success(payVO);
    }


    public Result getOrder(Long orderId) {
        RechargeOrder rechargeOrder = rechargeOrderRepository.findOne(orderId);
        if (rechargeOrder == null) {
            return Result.fail("充值订单不存在");
        }
        return Result.success(new RechargeOrderVO(rechargeOrder));

    }


    @Resource
    private PayHandlerFactory payHandlerFactory;
    

    public Map<String, String> callback(PayNotifyVO payNotifyVO) {
        long payInfoId = Long.parseLong(payNotifyVO.getOrder_id());
        PayInfo payInfo = payInfoRepository.findOne(payInfoId);
        AbPayHandler payHandler = payHandlerFactory.getPayHandler(payInfo.getOrderType());
        boolean callback = false;

        try {
            callback = payHandler.callback(payNotifyVO, payInfo);
        } catch (Exception e) {
            callback = false;
        }

        Map<String, String> map = new HashMap<>();
        if (callback) {
            map.put("statusCode", "0");
        } else {
            map.put("statusCode", "1");
        }

        return map;
    }


}
