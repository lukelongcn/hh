package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.model.dto.DidiCardDTO;
import com.h9.api.model.dto.DidiCardVerifyDTO;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.model.dto.MobileRechargeVerifyDTO;
import com.h9.api.provider.ChinaPayService;
import com.h9.api.provider.MobileRechargeService;
import com.h9.api.provider.SuNingProvider;
import com.h9.api.provider.model.SuNingContent;
import com.h9.api.provider.model.SuNingResult;
import com.h9.api.provider.model.WithdrawDTO;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.CharacterFilter;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MobileUtils;
import com.h9.common.utils.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.h9.common.db.entity.Orders.orderTypeEnum.VIRTUAL_GOODS;

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
    private UserRepository userRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private MobileRechargeService mobileRechargeService;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;
    @Resource
    private OrderService orderService;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private OrdersRepository ordersReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;
    @Resource
    private ChinaPayService chinaPayService;
    @Resource
    private WithdrawalsRecordRepository withdrawalsRecordReposiroty;
    @Resource
    private UserBankRepository userBankRepository;
    @Resource
    private WithdrawalsFailsReposiroty withdrawalsFailsReposiroty;
    @Resource
    private CommonService commonService;
    @Resource
    private CardCouponsRepository cardCouponsRepository;
    @Resource
    private WithdrawalsRequestReposiroty withdrawalsRequestReposiroty;
    @Resource
    private BankCardRepository bankCardRepository;
    @Value("${chinaPay.merId}")
    private String merId;
    @Resource
    private ConfigService configService;
    @Resource
    private RechargeRecordRepository rechargeRecordRepository;
    @Resource
    private SuNingProvider suNingProvider;


    @Resource
    private OfPayRecordReposiroty ofPayRecordReposiroty;
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private SmsService smsService;

    @Resource
    private GoodService goodService;

    public Result recharge(Long userId, MobileRechargeDTO mobileRechargeDTO) {
        OrderItems orderItems = new OrderItems();
        User user = userService.getCurrentUser(userId);
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();

        String recargeTel = mobileRechargeDTO.getTel();

        if (!MobileUtils.isMobileNO(recargeTel)) return Result.fail("请填写正确的手机号码");

        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");

        Goods goods = goodsReposiroty.findOne(mobileRechargeDTO.getId());
        BigDecimal realPrice = goods.getRealPrice();
        if (balance.compareTo(realPrice) < 0) {
            return Result.fail("余额不足");
        }

        //验证每日充值金额不能大于 300（配置）
        Result verifyTodayMoneyResult = verifyTodayMoney(realPrice, userId);
        if (verifyTodayMoneyResult.getCode() == 1) {
            return verifyTodayMoneyResult;
        }

        //校验 code
        String tel = user.getPhone();

        Result verifyResult = smsService.verifySmsCodeByType(userId, SMSTypeEnum.MOBILE_RECHARGE.getCode(), tel, mobileRechargeDTO.getCode());
        if (verifyResult != null) return verifyResult;


        String smsCodeCount = RedisKey.getSmsCodeCount(tel, SMSTypeEnum.MOBILE_RECHARGE.getCode());
        redisBean.expire(smsCodeCount, 1, TimeUnit.SECONDS);

        Orders order = orderService.initOrder(user.getNickName(), goods.getRealPrice(), mobileRechargeDTO.getTel() + "", VIRTUAL_GOODS.getCode() + "", "徽酒");
        order.setPayStatus(2);
        order.setUser(user);
        order.setOrderFrom(2);
        orderItems.setOrders(order);

        String balanceFlowType = configService.getValueFromMap(ParamConstant.BALANCE_FLOW_TYPE, "6");

        orderItems.setMoney(goods.getRealPrice());

        String balanceFlowImg = configService.getValueFromMap(ParamConstant.BALANCE_FLOW_IMG, "6");
        orderItems.setImage(balanceFlowImg);
        orderItems.setName(goods.getName());
        orderItems.setGoods(goods);

//        userAccountRepository.save(userAccount);
        String rechargeId = UUID.randomUUID().toString().replace("-", "");

        Result result = null;
        if (currentEnvironment.equals("product")) {
            logger.info("调用 recharge method");
            result = mobileRechargeService.recharge(mobileRechargeDTO, rechargeId, realPrice);
        } else {
            logger.info("调用 rechargeTest method");
            result = mobileRechargeService.rechargeTest(mobileRechargeDTO, rechargeId, realPrice);
        }

        //保存充值记录（包括失败成功）
        try {
            MobileRechargeService.Orderinfo orderinfo = (MobileRechargeService.Orderinfo) result.getData();
            OfPayRecord ofPayRecord = convertOfPayRecord(orderinfo, rechargeId);

            ofPayRecordReposiroty.save(ofPayRecord);

        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            logger.info("保存充值记录失败");
        }

        if (result.getCode() == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("time", DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND));
            map.put("money", MoneyUtils.formatMoney(realPrice));
            orderItemReposiroty.saveAndFlush(orderItems);
            saveRechargeRecord(user, goods.getRealPrice(), rechargeId, orderItems.getOrders().getId());
            addEveryDayRechargeMoney(userId, realPrice);
            commonService.setBalance(userId, order.getPayMoney().negate(), BalanceFlow.BalanceFlowTypeEnum.RECHARGE_PHONE_FARE.getId(), order.getId(), "", balanceFlowType);

            //减库存
//            Result changeStockResult = goodService.changeStock(goods);

            return Result.success("充值成功", map);
        } else {
//            order.setStatus(Orders.statusEnum.FAIL.getCode());
//            ordersReposiroty.save(order);
            return Result.fail("充值失败");
        }
    }

    private void addEveryDayRechargeMoney(Long userId, BigDecimal money) {
        String key = RedisKey.getTodayRechargeMoney(userId);
        String todayRechargeMoney = redisBean.getStringValue(key);

        if (StringUtils.isBlank(todayRechargeMoney)) {
            todayRechargeMoney = "0";
        }
        BigDecimal rechargedMoeny = new BigDecimal(todayRechargeMoney);

        rechargedMoeny = rechargedMoeny.add(money);

        redisBean.setStringValue(key, MoneyUtils.formatMoney(rechargedMoeny));
        redisBean.expire(key, DateUtil.getTimesNight());
    }

    private Result verifyTodayMoney(BigDecimal realPrice, Long userId) {
        try {
            String everydayRechargeTotalMoney = configService.getStringConfig("everydayRechargeTotalMoney");

            if (StringUtils.isBlank(everydayRechargeTotalMoney)) {
                everydayRechargeTotalMoney = "300";
            }
            String todayRechargeMoney = redisBean.getStringValue(RedisKey.getTodayRechargeMoney(userId));

            if (StringUtils.isBlank(todayRechargeMoney)) {
                todayRechargeMoney = "0";
            }
            BigDecimal rechargedMoney = realPrice.add((new BigDecimal(todayRechargeMoney)));
            if (rechargedMoney.compareTo(new BigDecimal(everydayRechargeTotalMoney)) > 0) {
                return Result.fail("每日充值金额不能超过" + MoneyUtils.formatMoney(new BigDecimal(everydayRechargeTotalMoney)));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return Result.fail("金额校验失败");
        }
        return Result.success();
    }


    public void saveRechargeRecord(User user, BigDecimal money, String rechargeId, Long orderId) {
        RechargeRecord rechargeRecord = new RechargeRecord(user.getId(), money, user.getNickName(), user.getPhone(), rechargeId, orderId);
        rechargeRecordRepository.save(rechargeRecord);
    }

    public OfPayRecord convertOfPayRecord(MobileRechargeService.Orderinfo orderinfo, String rechargeId) {

        OfPayRecord ofPayRecord = new OfPayRecord();
        ofPayRecord.setErrMsg(orderinfo.getErr_msg());
        ofPayRecord.setRetcode(orderinfo.getRetcode());
        ofPayRecord.setOrderid(orderinfo.getOrderid());
        ofPayRecord.setCardid(orderinfo.getCardid());
        ofPayRecord.setCardnum(orderinfo.getCardnum());
        ofPayRecord.setOrdercash(orderinfo.getOrdercash());
        ofPayRecord.setCardname(orderinfo.getCardname());
        ofPayRecord.setSporderId(rechargeId);
        ofPayRecord.setGameUserid(orderinfo.getGame_userid());
        ofPayRecord.setGameState(orderinfo.getGame_state());

        return ofPayRecord;

    }

    public Result rechargeDenomination(Long userId) {
        User user = userRepository.findOne(userId);
        GoodsType goodsType = goodsTypeReposiroty.findByCode("mobile_recharge");
        List<Goods> goodsList = goodsReposiroty.findByGoodsType(goodsType);
        if (goodsList == null) return Result.fail();
        List<Map<String, String>> list = new ArrayList<>();
        goodsList.forEach(goods -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", goods.getId() + "");
            map.put("price", MoneyUtils.formatMoney(goods.getPrice(), "0.00"));
            map.put("realPrice", goods.getRealPrice().toString());
            list.add(map);
        });
//        Map<String, String> telMap = new HashMap<>();

//        telMap.put("tel", user.getPhone());
//        list.add(telMap);
        Map<String, Object> mapVo = new HashMap<>();
        mapVo.put("priceList", list);
        mapVo.put("tel", user.getPhone());
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        mapVo.put("balance", MoneyUtils.formatMoney(userAccount.getBalance()));
        return Result.success(mapVo);
    }

    public Result didiCardList() {
//        List<DiDiCardInfo> realPriceAndStock = goodsReposiroty.findRealPriceAndStock();
        List<Map<String, Object>> list = new ArrayList<>();
        GoodsType goodsType = goodsTypeReposiroty.findByCode(GoodsType.GoodsTypeEnum.DIDI_CARD.getCode());
        if (goodsType == null) return Result.success(list);

        List<Goods> goodsList = goodsReposiroty.findByGoodsTypeAndStatus(goodsType);

        goodsList.stream().forEach(goods -> {
            Map<String, Object> map = new HashMap<>();
            map.put("imgUrl", goods.getImg());
//            Object count = cardCouponsRepository.getCount(goods.getId());
            map.put("stock", goods.getStock());
            map.put("name", goods.getName());
            map.put("goodId", goods.getId());
            map.put("price", MoneyUtils.formatMoney(goods.getRealPrice()));
            list.add(map);
        });
        return Result.success(list);
    }

    public Result didiCardConvert(DidiCardDTO didiCardDTO, Long userId) {
        User user = userRepository.findOne(userId);

        String phone = user.getPhone();
        String smsCodeKey = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.DIDI_CARD.getCode());

//        if (!didiCardDTO.getCode().equalsIgnoreCase(value)) return Result.fail("验证码不正确");

        Result verifyResult = smsService.verifySmsCodeByType(userId, SMSTypeEnum.DIDI_CARD.getCode(), user.getPhone(), didiCardDTO.getCode());
        if (verifyResult != null) return verifyResult;

        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);
        BigDecimal accountBalance = userAccount.getBalance();
        Long id = didiCardDTO.getId();

        Goods goods = goodsReposiroty.findOne(id);
        BigDecimal price = goods.getRealPrice();
        if (accountBalance.compareTo(price) < 0) {
            return Result.fail("余额不足");
        }

        if (goods == null) return Result.fail("商品不存在");

        CardCoupons cardCoupons = cardCouponsRepository.findByGoodsId(goods.getId());
        if (cardCoupons == null) return Result.fail("卡劵不存在");

        //生成订单
        Orders orders = orderService.initOrder(user.getNickName(), goods.getRealPrice(), user.getPhone(), Orders.orderTypeEnum.VIRTUAL_GOODS.getCode() + "", "徽酒");
        orders.setOrderFrom(2);
        orders.setUser(user);
        orders.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
        orders.setGoodsType(GoodsType.GoodsTypeEnum.DIDI_CARD.getCode());
        //修改库存
        Result changeResult = goodService.changeStock(goods);
        if (changeResult.getCode() == 1) {
            return changeResult;
        }

        ordersReposiroty.saveAndFlush(orders);
        OrderItems items = new OrderItems(goods.getName(), "", goods.getRealPrice(), goods.getRealPrice(), 1, orders);
        goodsReposiroty.save(goods);
        userAccountRepository.save(userAccount);
//        String smsCodeCountDown = RedisKey.getSmsCodeCountDown(user.getPhone(), SMSTypeEnum.CASH_RECHARGE.getCode());
        cardCoupons.setStatus(2);
        //余额操作
        commonService.setBalance(userId, goods.getRealPrice().negate(), 5L, orders.getId(), "", "滴滴卡充值");
        items.setDidiCardNumber(cardCoupons.getNo());
        items.setGoods(goods);

//        GlobalProperty globalProperty = globalPropertyRepository.findByCode(ParamConstant.BALANCE_FLOW_IMG);
//        Map<String,String> map = JSONObject.parseObject(globalProperty.getVal(), Map.class);

        Map<String, String> map = configService.getMapConfig(ParamConstant.BALANCE_FLOW_IMG);

        String value = map.get("5");
        items.setImage(value);


        Map<String, String> voMap = new HashMap<>();
        voMap.put("didiCardNumber", cardCoupons.getNo());
        voMap.put("money", goods.getRealPrice().toString());
        logger.info("key : " + smsCodeKey);
        orderItemReposiroty.save(items);
        cardCouponsRepository.save(cardCoupons);

        redisBean.expire(smsCodeKey, 1, TimeUnit.SECONDS);

        String smsCodeCountDown = RedisKey.getSmsCodeCountDown(user.getPhone(), SMSTypeEnum.DIDI_CARD.getCode());
        redisBean.expire(smsCodeCountDown, 1, TimeUnit.SECONDS);

        return Result.success(voMap);
    }


    public Result verifyConvertCoupons(DidiCardVerifyDTO didiCardDTO, Long userId) {
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);
        BigDecimal accountBalance = userAccount.getBalance();
        Long id = didiCardDTO.getId();

        Goods goods = goodsReposiroty.findOne(id);
        BigDecimal price = goods.getRealPrice();

        if (accountBalance.compareTo(price) < 0) {
            return Result.fail("余额不足");
        }

        if (goods == null) return Result.fail("商品不存在");

        CardCoupons cardCoupons = cardCouponsRepository.findByGoodsId(goods.getId());
        if (cardCoupons == null) return Result.fail("卡劵不存在");

        return Result.success("验证成功");
    }

    public Result bankWithDraw(Long userId, Long bankId, String code, double longitude, double latitude, HttpServletRequest request) {

        User user = userRepository.findOne(userId);
        //验证短信
        String smsCodeKey = RedisKey.getSmsCodeKey(user.getPhone(), SMSTypeEnum.CASH_RECHARGE.getCode());
        Result verifyResult = smsService.verifySmsCodeByType(userId, SMSTypeEnum.CASH_RECHARGE.getCode(), user.getPhone(), code);
        if (verifyResult != null) return verifyResult;

        UserBank userBank = userBankRepository.findOne(bankId);

        if (userBank == null) return Result.fail("请选择银行卡");

        BankType bankType = userBank.getBankType();
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();
        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");


        Result verifyWithdrawCount = verifyWithdrawCount(user);
        if (verifyWithdrawCount.getCode() == 1) {
            return verifyWithdrawCount;
        }

        String withdrawMax = configService.getStringConfig(ParamConstant.WITHDRAW_MAX);
        BigDecimal max = new BigDecimal(withdrawMax);

        //当天提现的金额
        Object todayWithdrawMoney = withdrawalsRecordReposiroty.findByTodayWithdrawMoney(userId);
        BigDecimal castTodayWithdrawMoney = null;

        if (todayWithdrawMoney == null) {
            castTodayWithdrawMoney = new BigDecimal(0);
        } else {
            castTodayWithdrawMoney = new BigDecimal(todayWithdrawMoney.toString());
        }

//        if (castTodayWithdrawMoney.compareTo(max) > 0) {
//            return Result.fail("您今日的提现金额超过每日额度");
//        }
//        BigDecimal willWithdrawMoney = castTodayWithdrawMoney.add(balance);
        //一天提现的金额最大值只能是最在额度值
        BigDecimal canWithdrawMoney = max.subtract(castTodayWithdrawMoney);
        if (balance.compareTo(canWithdrawMoney) < 0) {
            canWithdrawMoney = balance;
        }
        String transAmt = "";
        if (canWithdrawMoney.compareTo(new BigDecimal(0)) <= 0) {
            return Result.fail("您今日的提现金额超过每日额度");
        } else {
//            transAmt = canWithdrawMoney;
            if ("product".equals(currentEnvironment)) {
                transAmt = canWithdrawMoney.multiply(new BigDecimal(100)).toString();
            } else {
                transAmt = "101";
            }
        }

//        String cardNo = userBank.getNo();
//        String usrName = userBank.getName();
//        String openBank = bankType.getBankName();
//        String prov = userBank.getProvince();
//        String city = userBank.getCity();
        String purpose = "提现";
//        String signFlag = "1";
//        TODO
        canWithdrawMoney = new BigDecimal(1);
        WithdrawalsRecord withdrawalsRecord = new WithdrawalsRecord(user, canWithdrawMoney, userBank, purpose);
        withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);
        String merSeqId = String.valueOf(withdrawalsRecord.getId());

        WithdrawDTO withdrawDTO = new WithdrawDTO(userBank,canWithdrawMoney,withdrawalsRecord.getId(),merSeqId);
        Result withdrawResult = suNingProvider.withdraw(withdrawDTO);

        String smsCodeCountDown = RedisKey.getSmsCodeCountDown(user.getPhone(), SMSTypeEnum.CASH_RECHARGE.getCode());
        redisBean.expire(smsCodeCountDown, 1, TimeUnit.SECONDS);

        if(withdrawResult.isSuccess()){
            Map<String, String> map = new HashMap<>();
            map.put("time", DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND));
            map.put("money", "" + MoneyUtils.formatMoney(canWithdrawMoney));
            //转账成功
            withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.BANK_HANDLER.getCode());
            commonService.setBalance(withdrawalsRecord.getUserId(), withdrawalsRecord.getMoney().abs().negate(), 1L, withdrawalsRecord.getId(), withdrawalsRecord.getId()+"", "提现");
            return Result.success(map);
        }else{
            return withdrawResult;
        }

//
//        ChinaPayService.PayParam payParam = new ChinaPayService.PayParam(merSeqId, cardNo, usrName, openBank, prov, city, transAmt, signFlag, purpose);
//
//        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
//        String merDate = format.format(new Date());
//        Result result = chinaPayService.signPay(payParam, merDate, currentEnvironment);
//
//        //保存这个提现请求
//        WithdrawalsRequest withdrawalsRequest = new WithdrawalsRequest();
//        withdrawalsRequest.setWithdrawCashId(withdrawalsRecord.getId());
//        BeanUtils.copyProperties(payParam, withdrawalsRequest);
//        withdrawalsRequest.setBankReturnData(result.getData().toString());
//        withdrawalsRequest.setMerDate(merDate);
//        redisBean.expire(smsCodeKey, 1, TimeUnit.SECONDS);
//
//
//
//        UserRecord userRecord = commonService.newUserRecord(userId, latitude, longitude, request);
//        withdrawalsRecord.setUserRecord(userRecord);
//
//        if (result.getData().toString().startsWith("responseCode=0000")) {
//            if (result.getData().toString().contains("stat=s")) {
//                //转账成功
//                commonService.setBalance(userId, canWithdrawMoney.negate(), 1L, withdrawalsRecord.getId(), "", "提现");
//                withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.FINISH.getCode());
//            } else {
//                //转账尚未到用户卡上
//                withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.BANK_HANDLER.getCode());
//            }
//            BigDecimal oldWithdrawMoney = userBank.getWithdrawMoney();
//            oldWithdrawMoney = oldWithdrawMoney.add(balance);
//            Long withdrawCount = userBank.getWithdrawCount();
//            withdrawCount++;
//            userBank.setWithdrawCount(withdrawCount);
//            userBank.setWithdrawMoney(oldWithdrawMoney);
//
//            Map<String, String> map = new HashMap<>();
//            map.put("time", DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND));
//            map.put("money", "" + MoneyUtils.formatMoney(canWithdrawMoney));
//            withdrawalsRequestReposiroty.save(withdrawalsRequest);
//            withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);
//            bankCardRepository.save(userBank);
//            addWithdrawCount(user);
//            return Result.success(map);
//
//        } else {
//            //提现失败
//            withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.FAIL.getCode());
//            BeanUtils.copyProperties(payParam, withdrawalsRecord);
//            withdrawalsRecordReposiroty.save(withdrawalsRecord);
//            WithdrawalsFails withdrawalsFails = new WithdrawalsFails();
//            BeanUtils.copyProperties(payParam, withdrawalsFails);
//            withdrawalsFails.setBankReturnData(result.getData().toString());
//            withdrawalsFailsReposiroty.save(withdrawalsFails);
//            withdrawalsRequestReposiroty.save(withdrawalsRequest);
//            bankCardRepository.save(userBank);
//            return Result.fail("请确认银行卡信息是否正确");
//        }
    }

    /**
     * description: 验提现次数，一天3次 晚上 12 点清空
     */
    public Result verifyWithdrawCount(User user) {
        String withdrawSuccessCountKey = RedisKey.getWithdrawSuccessCountKey(user.getId());

        String count = redisBean.getStringValue(withdrawSuccessCountKey);

        if (StringUtils.isBlank(count)) {
            return Result.success();
        }
        int countInt = 0;
        try {
            countInt = Integer.valueOf(count);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(), e);
            return Result.fail();
        }
        logger.info("userId : " + user.getId() + " 已提现次数: " + countInt);
        if (countInt > 2) {
            return Result.fail("一天内提现次数不能超过3次，请明天再试");
        }

        return Result.success();
    }

    /**
     * description: 增加提现次数,晚上 12 点清空。
     */
    public void addWithdrawCount(User user) {
        String withdrawSuccessCountKey = RedisKey.getWithdrawSuccessCountKey(user.getId());
        String withdrawSuccessCount = redisBean.getStringValue(withdrawSuccessCountKey);
        //计算到晚上零点的毫秒值
        Date timesNight = DateUtil.getTimesNight();
        int delay = (int) (timesNight.getTime() - new Date().getTime());
        if (StringUtils.isBlank(withdrawSuccessCount)) {
            logger.info("userId :" + user.getId() + "今天第一次提现");
            redisBean.setStringValue(withdrawSuccessCountKey, "1", delay, TimeUnit.MILLISECONDS);
        } else {
            try {
                int count = Integer.valueOf(withdrawSuccessCount);
                count++;
                redisBean.setStringValue(withdrawSuccessCountKey, count + "", delay, TimeUnit.MILLISECONDS);
                logger.info("userId :" + user.getId() + "今天提现次数：" + count);

            } catch (NumberFormatException e) {
                logger.info(e.getMessage(), e);
                withdrawSuccessCount = "1";
                redisBean.setStringValue(withdrawSuccessCountKey, withdrawSuccessCount + "", delay, TimeUnit.MILLISECONDS);
            }
        }
    }

    @Value("${h9.current.envir}")
    private String currentEnvironment;

    public Result cz(Long userId) {
        if ("product".equals(currentEnvironment)) return Result.fail("此环境不支持");
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        userAccount.setBalance(new BigDecimal(201));
        userAccountRepository.save(userAccount);
        return Result.success();
    }

    public Result scan() {
        List<WithdrawalsRecord> withdrawCashRecord = withdrawalsRecordReposiroty.findByStatus(WithdrawalsRecord.statusEnum.BANK_HANDLER.getCode());

        String reduce = withdrawCashRecord.stream()
                .map(record -> record.getId() + "")
                .reduce("", (x, y) -> x + " ," + y);

        logger.info("没有到账的订单列表：" + reduce);
        //查询状态
        withdrawCashRecord.forEach(wr -> {
            WithdrawalsRequest withdrawRequest = withdrawalsRequestReposiroty.findByLastTry(wr.getId());
            if (withdrawRequest != null) {
                Result result = chinaPayService.query(withdrawRequest);
                String cpReturnData = result.getData().toString();
                logger.info("scan result : " + cpReturnData + " " + wr);

                if (cpReturnData.contains("|s|")) {
                    //此笔交易银行显示已完成了，把订单状态改变
                    logger.info("提现记录Id:" + wr.getId());
                    wr.setStatus(WithdrawalsRecord.statusEnum.FINISH.getCode());
                }

                if (cpReturnData.contains("|9|")) {
                    //此笔交易银联打款失败
                    wr.setStatus(WithdrawalsRecord.statusEnum.WITHDRA_EXPCETION.getCode());
//                    Long userId = wr.getUserId();
//                    commonService.setBalance(userId, wr.getMoney(), 2L, wr.getId(), "", "银联退回");
                }

                withdrawalsRecordReposiroty.save(wr);
            }

        });
        return null;
    }


    @SuppressWarnings("Duplicates")
    public Result withdraInfo(Long userId) {
        List<UserBank> userBankList = bankCardRepository.findByUserIdAndStatus(userId, 1);
        List<Map<String, String>> bankList = new ArrayList<>();
        userBankList.forEach(bank -> {

            if (bank.getStatus() == 1) {
                Map<String, String> map = new HashMap<>();
                map.put("bankImg", bank.getBankType().getBankImg());
                map.put("name", bank.getBankType().getBankName());
                String no = bank.getNo();
                int length = no.length();

                map.put("no", CharacterFilter.hiddenBankCardInfo(no));

                map.put("id", bank.getId() + "");
                map.put("color", bank.getBankType().getColor());
                bankList.add(map);

            }

        });

        Map<String, Object> infoVO = new HashMap<>();
        infoVO.put("bankList", bankList);
//        GlobalProperty globalProperty = globalPropertyRepository.findByCode("withdrawMax");
//        String max = globalProperty.getVal();

        String max = configService.getStringConfig("withdrawMax");
        if (StringUtils.isBlank(max)) {
            max = "1000";
            logger.error("没有找到提现最大值参数，默认为: " + max);
        }

        infoVO.put("withdrawalCount", max);
        BigDecimal canWithdrawMoney = getUserWithdrawTodayMoney(userId);
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        infoVO.put("balance", MoneyUtils.formatMoney(userAccount.getBalance()));
        infoVO.put("withdrawMoney", MoneyUtils.formatMoney(canWithdrawMoney));

        return Result.success(infoVO);
    }

    /**
     * description: 获取指定用户今日还可提现的金额
     */
    public BigDecimal getUserWithdrawTodayMoney(Long userId) {

        String max = configService.getStringConfig("withdrawMax");
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        BigDecimal balance = userAccount.getBalance();
        BigDecimal todayWithdrawMoney = withdrawalsRecordReposiroty.todayWithdrawMoney(userId);

        BigDecimal maxBigDecimal = new BigDecimal(max);
        BigDecimal canWithdrawMoney = maxBigDecimal.subtract(todayWithdrawMoney);

        if (canWithdrawMoney.compareTo(new BigDecimal(0)) < 0) {
            return new BigDecimal(0);
        }

        if (canWithdrawMoney.compareTo(balance) >= 0) {
            return balance;
        } else {
            return canWithdrawMoney;
        }
    }

    public Result bankWithDrawVerify(Long userId, Long bankId, double longitude, double latitude, HttpServletRequest request) {

        UserBank userBank = userBankRepository.findOne(bankId);

        if (userBank == null) return Result.fail("请选择银行卡");

        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();
        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");

        String withdrawMax = configService.getStringConfig("withdrawMax");
        BigDecimal max = new BigDecimal(withdrawMax);

        //当天提现的金额
        Object todayWithdrawMoney = withdrawalsRecordReposiroty.findByTodayWithdrawMoney(userId);
        BigDecimal castTodayWithdrawMoney = null;

        if (todayWithdrawMoney == null) {
            castTodayWithdrawMoney = new BigDecimal(0);
        } else {
            castTodayWithdrawMoney = new BigDecimal(todayWithdrawMoney.toString());
        }

        BigDecimal canWithdrawMoney = max.subtract(castTodayWithdrawMoney);
        if (canWithdrawMoney.compareTo(new BigDecimal(0)) <= 0) {
            return Result.fail("您今日的提现金额超过每日额度");
        }

        return Result.success();

    }

    public Result rechargeVerify(Long userId, MobileRechargeVerifyDTO mobileRechargeDTO) {
        User user = userService.getCurrentUser(userId);
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();

        String recargeTel = mobileRechargeDTO.getTel();

        if (!MobileUtils.isMobileNO(recargeTel)) return Result.fail("请填写正确的手机号码");

        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");

        Goods goods = goodsReposiroty.findOne(mobileRechargeDTO.getId());
        BigDecimal realPrice = goods.getRealPrice();
        if (balance.compareTo(realPrice) < 0) {
            return Result.fail("余额不足");
        }
        return Result.success("校验成功");
    }


    public String callback(SuNingResult suNingResult){
        if(suNingResult == null){
            return "";
        }
        SuNingContent content = suNingResult.getContent();
        if (content == null) {
            return "";
        }

        String batchNo = content.getBatchNo();
        Long orderId = Long.parseLong(batchNo);
        String status = content.getStatus();
        WithdrawalsRecord withdrawalsRecord = withdrawalsRecordReposiroty.findByLockId(orderId);
        if(withdrawalsRecord.getStatus() == WithdrawalsRecord.statusEnum.FINISH.getCode()){
            return "true";
        }

        if ( "07".equals(status) ) {
            withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.FINISH.getCode());
            withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);

            //设置默认银行卡
            UserBank defaulBank = bankCardRepository.getDefaultBank(withdrawalsRecord.getUserId());
            if (defaulBank != null) {
                defaulBank.setDefaultSelect(0);
                bankCardRepository.save(defaulBank);
            }
            UserBank userBank = withdrawalsRecord.getUserBank();
            userBank.setDefaultSelect(1);
            bankCardRepository.save(userBank);

            BigDecimal oldWithdrawMoney = userBank.getWithdrawMoney();
            oldWithdrawMoney = oldWithdrawMoney.add(withdrawalsRecord.getMoney());
            Long withdrawCount = userBank.getWithdrawCount();
            withdrawCount++;
            userBank.setWithdrawCount(withdrawCount);
            userBank.setWithdrawMoney(oldWithdrawMoney);

            bankCardRepository.save(userBank);
            Long userId = withdrawalsRecord.getUserId();
            User user = userRepository.findOne(userId);
            addWithdrawCount(user);
            return "true";
        }else if("04".equals(status) ){
            withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.FAIL.getCode());
            withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);
        }
        return "false";
    }
}
