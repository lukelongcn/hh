package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.vo.BalanceFlowVO;
import com.h9.api.model.vo.MyCouponsVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.api.model.vo.UserAccountInfoVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:账号
 * BalanceService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 16:50
 */
@Component
public class AccountService {
    @Resource
    private BalanceFlowRepository balanceFlowRepository;
    @Resource
    private VCoinsFlowRepository vCoinsFlowRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserBankRepository userBankRepository;
    @Resource
    private OrdersReposiroty ordersReposiroty;
    @Resource
    private GlobalPropertyRepository globalPropertyRepository;


    public Result getBalanceFlow(Long userId, int page, int limit) {

        PageRequest pageRequest = balanceFlowRepository.pageRequest(page, limit);
        Page<BalanceFlow> balanceFlows = balanceFlowRepository.findByBalance(userId, pageRequest);
        PageResult<BalanceFlow> flowPageResult = new PageResult<>(balanceFlows);
        GlobalProperty val = globalPropertyRepository.findByCode("balanceFlowImg");
        Map iconMap = JSONObject.parseObject(val.getVal(), Map.class);
        return Result.success(flowPageResult.result2Result(bf -> new BalanceFlowVO(bf, iconMap)));

    }

    public Result getVCoinsFlow(Long userId, int page, int limit) {
        PageRequest pageRequest = balanceFlowRepository.pageRequest(page, limit);
        Page<VCoinsFlow> balanceFlows = vCoinsFlowRepository.findByBalance(userId, pageRequest);
        PageResult<VCoinsFlow> flowPageResult = new PageResult<>(balanceFlows);
        return Result.success(flowPageResult.result2Result(BalanceFlowVO::new));
    }

    public BigDecimal getAccountBalance(Long userId) {
        UserAccount userAccount = userAccountRepository.findOne(userId);
        if (userAccount == null) return new BigDecimal(0);

        return userAccount.getBalance();
    }

    public Result accountInfo(Long userId) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        User user = userRepository.findOne(userId);
        Object cardCount = orderItemReposiroty.findCardCount(userId, Orders.orderTypeEnum.DIDI_COUPON.getCode());
        List<Map<String, String>> bankList = new ArrayList<>();
//        List<UserBank> userBankList = userBankRepository.findByUserId(userId);

//        userBankList.forEach(bank -> {
//
//            if (bank.getStatus() == 1) {
//                Map<String, String> map = new HashMap<>();
//                map.put("bankImg", bank.getBankType().getBankImg());
//                map.put("name", bank.getVal());
//                String no = bank.getNo();
//                int length = no.length();
//                no = no.substring(length - 4, length);
//                map.put("no", no);
//                map.put("id", bank.getId() + "");
//                bankList.add(map);
//            }
//
//        });
        UserAccountInfoVO userAccountInfoVO = new UserAccountInfoVO(user, userAccount, cardCount + "", bankList);
        return Result.success(userAccountInfoVO);
    }

    public Result couponeList(Long userId, int page, int limit) {

        PageRequest pageRequest = orderItemReposiroty.pageRequest(page, limit);
        Page<Orders> orders = ordersReposiroty.findByUser(userId, pageRequest);

        return Result.success(new PageResult<>(orders).result2Result(ord -> {

            List<OrderItems> list = ord.getOrderItems();
            if (!CollectionUtils.isEmpty(list)) {
                OrderItems orderItems = list.get(0);
                MyCouponsVO myCouponsVO = new MyCouponsVO(orderItems);
                return myCouponsVO;
            }
            return null;
        }));

    }
}
