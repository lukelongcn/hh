package com.h9.api.service;

import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.provider.MobileRechargeService;
import com.h9.api.provider.SMService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.OrderItems;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by itservice on 2017/10/31.
 */
@Service
public class ConsumeService {

    @Resource
    private UserService userService;
    @Resource
    private RedisBean redisBean;
    @Resource
    private SMService smService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private UserAccountReposiroty userAccountReposiroty;
    @Resource
    private UserExtendsReposiroty userExtendsReposiroty;
    @Resource
    private MobileRechargeService mobileRechargeService;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;
    @Resource
    private OrderService orderService;
    @Resource
    private GoodsReposiroty goodsReposiroty;

    public Result recharge(MobileRechargeDTO mobileRechargeDTO) {
        //TODO 判断 余额 够不够
        //TODO 防止用户连续多次点击，多次充值的情况
        OrderItems orderItems = new OrderItems();
        User user = userService.getCurrentUser();

        Orders order = orderService.initOrder(user.getNickName(),new BigDecimal(mobileRechargeDTO.getCardNum()),mobileRechargeDTO.getTel()+"");

        orderItems.setOrders(order);
        Result result = mobileRechargeService.recharge(mobileRechargeDTO);
        orderItems.setMoney(new BigDecimal(mobileRechargeDTO.getCardNum()));
        orderItems.setName("手机话费充值");

        orderItemReposiroty.saveAndFlush(orderItems);

        if (result.getCode() == 0) {
            return Result.success("充值成功");
        }else{
            return Result.fail("充值失败");
        }
    }

    public Result rechargeDenomination() {

        List<Goods> all = goodsReposiroty.findAll();


        return Result.success(all);
    }
}
