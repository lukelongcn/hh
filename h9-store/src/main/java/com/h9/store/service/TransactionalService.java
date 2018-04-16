package com.h9.store.service;

import com.h9.common.base.BaseRepository;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.bigrich.OrdersLotteryActivity;
import com.h9.common.db.entity.bigrich.OrdersLotteryRelation;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.OrdersLotteryRelationRep;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.utils.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于调用新的事务
 */
@Service
public class TransactionalService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T findOneNewTrans(BaseRepository<T> rep,Long id) {

        return rep.findOne(id);
    }
    @Resource
    private CommonService commonService;

    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private OrdersLotteryRelationRep ordersLotteryRelationRep;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<Object, Object> showJoinIn(Orders order, User user, Goods goods) {
        Map<Object, Object> mapVo = new HashMap<>();
        OrdersLotteryActivity ordersLotteryActivity = commonService.joinBigRich(order);
        if (ordersLotteryActivity != null) {
            //判断是否以前参与过此次活动
            List<Orders> ordersList = ordersRepository.findByordersLotteryIdAndUser(ordersLotteryActivity.getId(), user);

            OrdersLotteryRelation ordersLotteryRelation = new OrdersLotteryRelation(null, user.getId(),
                    order.getId(), ordersLotteryActivity.getId(), 0, null);
            ordersLotteryRelationRep.save(ordersLotteryRelation);
            if (CollectionUtils.isNotEmpty(ordersList) && ordersList.size() ==1) {
                logger.info("真实参与记录 " + ordersList.size());
                mapVo.put("activityName", "1号大富贵");
                mapVo.put("lotteryChance", "获得1次抽奖机会");
                logger.debug("获得一次抽奖机会");
            }

        }
        mapVo.put("price", MoneyUtils.formatMoney(goods.getRealPrice()));
        mapVo.put("goodsName", goods.getName());
        mapVo.put("resumePaywxjs", false);
        return mapVo;
    }
}
