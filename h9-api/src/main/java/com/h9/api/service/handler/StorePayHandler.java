package com.h9.api.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.PayNotifyVO;
import com.h9.api.service.BigRichService;
import com.h9.api.service.GoodService;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.bigrich.OrdersLotteryActivity;
import com.h9.common.db.entity.bigrich.OrdersLotteryRelation;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.OrderItems;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.*;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.h9.common.db.entity.order.Orders.PayMethodEnum.WX_PAY;

/**
 * Created by itservice on 2018/1/17.
 */
@Component
public class StorePayHandler extends AbPayHandler {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PayInfoRepository payInfoRepository;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private HotelOrderRepository hotelOrderRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;
    @Resource
    private GoodService goodService;
    @Resource
    private BigRichService bigRichService;
    @Resource
    private UserCouponsRepository userCouponsRepository;
    @Resource
    private OrdersLotteryRelationRep ordersLotteryRelationRep;

    @Override
    public boolean callback(PayNotifyVO payNotifyVO, PayInfo payInfo) {
        logger.info("商城支付callBack: payNotifyVO : " + JSONObject.toJSONString(payNotifyVO)
                + " payInfo: " + JSONObject.toJSONString(payInfo));
        Integer status = payInfo.getStatus();
        if (status == 2) {
            return true;
        }
        payInfo.setStatus(2);
        payInfoRepository.save(payInfo);

        Long orderId = payInfo.getOrderId();
        Orders orders = ordersRepository.findOne(orderId);
        //改变劵的状态
        UserCoupon userCoupon = userCouponsRepository.findByOrderId(orderId);
        if (userCoupon != null) {
            userCoupon.setState(UserCoupon.statusEnum.USED.getCode());
            userCouponsRepository.save(userCoupon);
        }
        BigDecimal payMoney4Wechat = payInfo.getMoney();
//        payMoney4Wechat = payMoney4Wechat.add(payInfo.getMoney());
        orders.setStatus(Orders.statusEnum.WAIT_SEND.getCode());
        orders.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
        orders.setPayMethond(WX_PAY.getCode());
        ordersRepository.saveAndFlush(orders);

        // 参与大富贵活动
        OrdersLotteryActivity ordersLotteryActivity = commonService.joinBigRich(orders);

        User user = orders.getUser();
        if (ordersLotteryActivity != null) {
            //判断是否以前参与过此次活动
            List<Orders> ordersList = ordersRepository.findByordersLotteryIdAndUser(ordersLotteryActivity.getId(), user);
            if (CollectionUtils.isNotEmpty(ordersList)) {
                logger.info("真实参与记录 " + ordersList.size());

                OrdersLotteryRelation ordersLotteryRelation = new OrdersLotteryRelation(null, user.getId(),
                        orders.getId(), ordersLotteryActivity.getId(), 0, null);
                ordersLotteryRelationRep.save(ordersLotteryRelation);
            }
        }

//       bigRichService.joinBigRich(orders);


        //记录两条流水
        commonService.setBalance(orders.getUser().getId(), payInfo.getMoney().abs(),
                BalanceFlow.BalanceFlowTypeEnum.Recharge.getId(), orderId, "",
                BalanceFlow.BalanceFlowTypeEnum.Recharge.getName());

        commonService.setBalance(orders.getUser().getId(), payInfo.getMoney().abs().negate(),
                BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getId(), orderId, "",
                BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getName());
        List<OrderItems> orderItems = orders.getOrderItems();
        if (CollectionUtils.isNotEmpty(orderItems)) {

            OrderItems items = orderItems.get(0);
            Goods goods = items.getGoods();
            Result result = goodService.changeStock(goods, items.getCount());
        }
        return true;
    }
}
