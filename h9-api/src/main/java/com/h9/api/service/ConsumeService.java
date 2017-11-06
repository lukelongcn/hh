package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
//import com.h9.api.provider.ChinaPayService;
//import com.h9.api.provider.ChinaPayService;
import com.h9.api.provider.ChinaPayService;
import com.h9.common.modle.DiDiCardInfo;
import com.h9.api.model.dto.DidiCardDTO;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.provider.MobileRechargeService;
import com.h9.api.provider.SMService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2017/10/31.
 */
@Service
@Transactional
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
    private UserAccountRepository userAccountRepository;
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
    @Resource
    private OrdersReposiroty ordersReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;
    @Resource
    private ChinaPayService chinaPayService;
    @Resource
    private WithdrawalsRecordReposiroty withdrawalsRecordReposiroty;
    @Resource
    private AccountService accountService;
    @Resource
    private UserBankRepository userBankRepository;
    @Resource
    private WithdrawalsFailsReposiroty withdrawalsFailsReposiroty;
    @Value("${chinaPay.merId}")
    private String merId;


    private Logger logger = Logger.getLogger(this.getClass());

    public Result recharge(Long userId, MobileRechargeDTO mobileRechargeDTO) {
        BigDecimal accountBalance = accountService.getAccountBalance(userId);
        //TODO 防止用户连续多次点击，多次充值的情况
        //TODO 记录充值日志
        OrderItems orderItems = new OrderItems();
        User user = userService.getCurrentUser(userId);
//        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();

        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");

        Goods goods = goodsReposiroty.findOne(mobileRechargeDTO.getId());
        BigDecimal realPrice = goods.getRealPrice();
        if (balance.compareTo(realPrice) < 0) {
            return Result.fail("余额不足");
        }

        BigDecimal subtract = balance.subtract(realPrice);

//        userAccountRepository.changeBalance(subtract, userId);
        userAccount.setBalance(subtract);

        Orders order = orderService.initOrder(user.getNickName(), new BigDecimal(50), mobileRechargeDTO.getTel() + "", Orders.orderTypeEnum.MOBILE_RECHARGE.getCode(), "滴滴");
        order.setUser(user);
        orderItems.setOrders(order);
        Result result = mobileRechargeService.recharge(mobileRechargeDTO);
        orderItems.setMoney(new BigDecimal(50));
        orderItems.setName("手机话费充值");

        orderItemReposiroty.saveAndFlush(orderItems);
        userAccountRepository.save(userAccount);
        if (result.getCode() == 0) {
            return Result.success("充值成功");
        } else {
            throw new RuntimeException("充值失败");
        }
    }

    public Result rechargeDenomination() {

        GoodsType goodsType = goodsTypeReposiroty.findOne(1L);
        List<Goods> goodsList = goodsReposiroty.findByGoodsType(goodsType);
        if(goodsList == null) return Result.fail();
        List<Map<String, String>> list = new ArrayList<>();
        goodsList.forEach(goods -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", goods.getId() + "");
            map.put("price", goods.getPrice().toString());
            map.put("realPrice", goods.getRealPrice().toString());
            list.add(map);
        });
        return Result.success(list);
    }

    public Result didiCardList() {
        List<DiDiCardInfo> realPriceAndStock = goodsReposiroty.findRealPriceAndStock();
        Map<String, Object> map = new HashMap<>();
        map.put("didiCardList", realPriceAndStock);
        map.put("balance", "10");
        return Result.success(map);
    }

    public Result didiCardConvert(DidiCardDTO didiCardDTO, Long userId) {
        User user = userRepository.findOne(userId);

        String phone = user.getPhone();
        String smsCodeKey = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.DIDI_CARD.getCode());
        String value = redisBean.getStringValue(smsCodeKey);

        if (!didiCardDTO.getCode().equalsIgnoreCase(value)) return Result.fail("验证码不正确");

        BigDecimal accountBalance = accountService.getAccountBalance(userId);
        BigDecimal price = didiCardDTO.getPrice();

        if (accountBalance.compareTo(price) < 0) {
            return Result.fail("余额不足");
        }

        Goods goods = goodsReposiroty.findByTop1();
        goods.setStatus(0);
        //生成订单
        Orders orders = orderService.initOrder(user.getNickName(), didiCardDTO.getPrice(), user.getPhone(), Orders.orderTypeEnum.DIDI_COUPON.getCode(), "滴滴");
        orders.setUser(user);

        ordersReposiroty.saveAndFlush(orders);
        OrderItems items = new OrderItems("滴滴卡兑换", "", didiCardDTO.getPrice(), didiCardDTO.getPrice(), 1, orders);
        orderItemReposiroty.save(items);
        goodsReposiroty.save(goods);
        //返回数据
        Map<String, String> voMap = new HashMap<>();
        voMap.put("didiCardNumber", goods.getDiDiCardNumber());
        voMap.put("money", goods.getRealPrice().toString());
        logger.info("key : " + smsCodeKey);
        redisBean.setStringValue(smsCodeKey, "", 1, TimeUnit.SECONDS);
        return Result.success(voMap);
    }

    public Result bankWithDraw(Long userId, Long bankId, String code) {

        //TODO 额度校验
        User user = userRepository.findOne(userId);
        //验证短信
        String smsCodeKey = RedisKey.getSmsCodeKey(user.getPhone(), SMSTypeEnum.CASH_RECHARGE.getCode());
        String redisCode = redisBean.getStringValue(smsCodeKey);
        if (!code.equals(redisCode)) return Result.fail("验证码不正确");

        UserBank userBank = userBankRepository.findOne(bankId);
        BankType bankType = userBank.getBankType();
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();
        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");

        String cardNo = userBank.getNo();
        String usrName = userBank.getName();
        String openBank = bankType.getBankName();
        String prov = userBank.getProvice();
        String city = userBank.getCity();
        String transAmt = "101";
        String purpose = "提现";
//        String version = "20151207";
        String signFlag = "1";
//        String termType = "7";

        WithdrawalsRecord withdrawalsRecord = new WithdrawalsRecord(userId, new BigDecimal(transAmt), userBank, purpose);
        withdrawalsRecord.setBankReturnData("");
        withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);
        String merSeqId = String.valueOf(withdrawalsRecord.getId());

        ChinaPayService.PayParam payParam = new ChinaPayService.PayParam(merSeqId, cardNo, usrName, openBank, prov, city, transAmt, signFlag, purpose);
        Result result = chinaPayService.signPay(payParam);
        withdrawalsRecord.setBankReturnData(result.getData().toString());
        if (result.getData().toString().startsWith("responseCode=0000")) {
            //转账成功
            withdrawalsRecord.setStatus(3);
            userAccount.setBalance(new BigDecimal(0));
            userAccountRepository.save(userAccount);
            BeanUtils.copyProperties(payParam, withdrawalsRecord);
            withdrawalsRecordReposiroty.save(withdrawalsRecord);
            return Result.success();
        } else {
            //提现失败
            withdrawalsRecord.setStatus(4);
            BeanUtils.copyProperties(payParam, withdrawalsRecord);
            withdrawalsRecordReposiroty.save(withdrawalsRecord);
            WithdrawalsFails withdrawalsFails = new WithdrawalsFails();
            BeanUtils.copyProperties(payParam, withdrawalsFails);
            withdrawalsFails.setBankReturnData(result.getData().toString());
            withdrawalsFailsReposiroty.save(withdrawalsFails);
            return Result.fail();
        }
    }

    public Result cz(Long userId) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        userAccount.setBalance(new BigDecimal(200));
        userAccountRepository.save(userAccount);
        return Result.success();
    }
}
