package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.provider.ChinaPayService;
import com.h9.common.common.CommonService;
import com.h9.api.model.dto.DidiCardDTO;
import com.h9.api.model.dto.MobileRechargeDTO;
import com.h9.api.provider.MobileRechargeService;
import com.h9.api.provider.SMSProvide;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private SMSProvide smService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserExtendsRepository userExtendsRepository;
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
    private AccountService accountService;
    @Resource
    private UserBankRepository userBankRepository;
    @Resource
    private WithdrawalsFailsReposiroty withdrawalsFailsReposiroty;
    @Resource
    private BalanceFlowRepository balanceFlowRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private GoodsDIDINumberRepository goodsDIDINumberRepository;
    @Resource
    private WithdrawalsRequestReposiroty withdrawalsRequestReposiroty;
    @Resource
    private BankCardRepository bankCardRepository;
    @Value("${chinaPay.merId}")
    private String merId;
    @Resource
    private GlobalPropertyRepository globalPropertyRepository;
    @Resource
    private ConfigService configService;

    private Logger logger = Logger.getLogger(this.getClass());

    public Result recharge(Long userId, MobileRechargeDTO mobileRechargeDTO) {
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

        Orders order = orderService.initOrder(user.getNickName(), new BigDecimal(50), mobileRechargeDTO.getTel() + "", GoodsType.GoodsTypeEnum.MOBILE_RECHARGE.getCode(), "徽酒");
        order.setUser(user);
        orderItems.setOrders(order);
        commonService.setBalance(userId, order.getPayMoney().negate(), 4L, order.getId(), "", "话费充值");
        Result result = mobileRechargeService.recharge(mobileRechargeDTO);
        orderItems.setMoney(goods.getRealPrice());
        orderItems.setName("手机话费充值");

        orderItemReposiroty.saveAndFlush(orderItems);
        userAccountRepository.save(userAccount);
        if (result.getCode() == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("time", DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND));
            map.put("money", "" + realPrice.setScale(2, RoundingMode.DOWN));
            return Result.success("充值成功", map);
        } else {
            throw new RuntimeException("充值失败");
        }
    }

    public Result rechargeDenomination(Long userId) {
        User user = userRepository.findOne(userId);
        GoodsType goodsType = goodsTypeReposiroty.findOne(1L);
        List<Goods> goodsList = goodsReposiroty.findByGoodsType(goodsType);
        if (goodsList == null) return Result.fail();
        List<Map<String, String>> list = new ArrayList<>();
        goodsList.forEach(goods -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", goods.getId() + "");
            map.put("price", goods.getPrice().toString());
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
        mapVo.put("balance", userAccount.getBalance().setScale(2, RoundingMode.DOWN).toString());
        return Result.success(mapVo);
    }

    public Result didiCardList() {
//        List<DiDiCardInfo> realPriceAndStock = goodsReposiroty.findRealPriceAndStock();
        List<Map<String, Object>> list = new ArrayList<>();
        GoodsType goodsType = goodsTypeReposiroty.findOne(new Long(GoodsType.GoodsTypeEnum.DIDI_CARD.getCode()));
        if (goodsType == null) return Result.fail();

        List<Goods> goodsList = goodsReposiroty.findByGoodsType(goodsType);

        goodsList.forEach(goods -> {
            Map<String, Object> map = new HashMap<>();
            map.put("imgUrl", goods.getImg());
            Object count = goodsDIDINumberRepository.getCount(goods.getId());
            map.put("stock", count);
            map.put("name", goods.getName());
            map.put("goodId", goods.getId());
            map.put("price", goods.getRealPrice());
            list.add(map);
        });
        return Result.success(list);
    }

    public Result didiCardConvert(DidiCardDTO didiCardDTO, Long userId) {
        User user = userRepository.findOne(userId);

        String phone = user.getPhone();
        String smsCodeKey = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.DIDI_CARD.getCode());
        String value = redisBean.getStringValue(smsCodeKey);
        if (!didiCardDTO.getCode().equalsIgnoreCase(value)) return Result.fail("验证码不正确");
        redisBean.expire(smsCodeKey, 1, TimeUnit.SECONDS);

        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);
        BigDecimal accountBalance = userAccount.getBalance();
        Long id = didiCardDTO.getId();

        Goods goods = goodsReposiroty.findOne(id);
        BigDecimal price = goods.getPrice();
        if (accountBalance.compareTo(price) < 0) {
            return Result.fail("余额不足");
        }

        if (goods == null) return Result.fail("商品不存在");
        goods.setStatus(0);
        //生成订单
        Orders orders = orderService.initOrder(user.getNickName(), goods.getRealPrice(), user.getPhone(), GoodsType.GoodsTypeEnum.DIDI_CARD.getCode(), "徽酒");
        orders.setUser(user);

        ordersReposiroty.saveAndFlush(orders);
        OrderItems items = new OrderItems("滴滴卡兑换", "", goods.getRealPrice(), goods.getRealPrice(), 1, orders);
        goodsReposiroty.save(goods);
        userAccountRepository.save(userAccount);
        //
        //余额操作
        commonService.setBalance(userId, goods.getRealPrice().negate(), 5L, orders.getId(), "", "滴滴卡充值");
        //返回数据
        PageRequest pageRequest = new PageRequest(0, 1);
        GoodsDIDINumber goodsDIDINumber = goodsDIDINumberRepository.findByGoodsId(goods.getId());
        goodsDIDINumber.setStatus(2);

        items.setDidiCardNumber(goodsDIDINumber.getDidiNumber());
        items.setGoods(goods);

//        GlobalProperty globalProperty = globalPropertyRepository.findByCode("balanceFlowImg");
//        Map<String,String> map = JSONObject.parseObject(globalProperty.getVal(), Map.class);

        Map<String, String> map = configService.getMapConfig("balanceFlowImg");
        map.forEach((k, v) -> {
            if (k.equals("5")) {
                items.setImage(v);
                return;
            }
        });

        Map<String, String> voMap = new HashMap<>();
        voMap.put("didiCardNumber", goodsDIDINumber.getDidiNumber());
        voMap.put("money", goods.getRealPrice().toString());
        logger.info("key : " + smsCodeKey);
        redisBean.setStringValue(smsCodeKey, "", 1, TimeUnit.SECONDS);
        orderItemReposiroty.save(items);
        goodsDIDINumberRepository.save(goodsDIDINumber);
        return Result.success(voMap);
    }

    public Result bankWithDraw(Long userId, Long bankId, String code, double longitude, double latitude, HttpServletRequest request) {

        User user = userRepository.findOne(userId);
        //验证短信
        String smsCodeKey = RedisKey.getSmsCodeKey(user.getPhone(), SMSTypeEnum.CASH_RECHARGE.getCode());
        String redisCode = redisBean.getStringValue(smsCodeKey);
        if (!code.equals(redisCode)) return Result.fail("验证码不正确");
        redisBean.expire(smsCodeKey, 1, TimeUnit.SECONDS);

        UserBank userBank = userBankRepository.findOne(bankId);
        BankType bankType = userBank.getBankType();
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);

        BigDecimal balance = userAccount.getBalance();
        if (balance.compareTo(new BigDecimal(0)) <= 0) return Result.fail("余额不足");

        String withdrawMax = configService.getStringConfig("withdrawMax");
        BigDecimal max = new BigDecimal(withdrawMax);

//        if (balance.compareTo(max) >= 0) {
//            return Result.fail("超过了每日额度");
//        }

        //当天提现的金额
        Object todayWithdrawMoney = withdrawalsRecordReposiroty.findByTodayWithdrawMoney(userId);
        BigDecimal castTodayWithdrawMoney = new BigDecimal(todayWithdrawMoney.toString());
        BigDecimal willWithdrawMoney = castTodayWithdrawMoney.add(balance);
        if (willWithdrawMoney.compareTo(max) > 0) {
            return Result.fail("提现金额超过每日额度");

        }

        String cardNo = userBank.getNo();
        String usrName = userBank.getName();
        String openBank = bankType.getBankName();
        String prov = userBank.getProvince();
        String city = userBank.getCity();
        String transAmt = "101";
        String purpose = "提现";
        String signFlag = "1";

        WithdrawalsRecord withdrawalsRecord = new WithdrawalsRecord(userId, new BigDecimal(transAmt), userBank, purpose);
        withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);
        String merSeqId = String.valueOf(withdrawalsRecord.getId());

        ChinaPayService.PayParam payParam = new ChinaPayService.PayParam(merSeqId, cardNo, usrName, openBank, prov, city, transAmt, signFlag, purpose);

        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String merDate = format.format(new Date());
        Result result = chinaPayService.signPay(payParam, merDate);

        //保存这个提现请求
        WithdrawalsRequest withdrawalsRequest = new WithdrawalsRequest();
        withdrawalsRequest.setWithdrawCashId(withdrawalsRecord.getId());
        BeanUtils.copyProperties(payParam, withdrawalsRequest);
        withdrawalsRequest.setBankReturnData(result.getData().toString());
        withdrawalsRequest.setMerDate(merDate);

        //设置默认银行卡
        UserBank defaulBank = bankCardRepository.getDefaultBank(userId);
        if (defaulBank != null) {
            defaulBank.setDefaultSelect(0);
            bankCardRepository.save(defaulBank);
        }
        userBank.setDefaultSelect(1);
        bankCardRepository.save(userBank);
        UserRecord userRecord = commonService.newUserRecord(userId, latitude, longitude, request);
        withdrawalsRecord.setUserRecord(userRecord);

        if (result.getData().toString().startsWith("responseCode=0000")) {
            if (result.getData().toString().contains("stat=s")) {
                //转账成功
                commonService.setBalance(userId, balance.negate(), 1L, withdrawalsRecord.getId(), "", "提现");
                withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.FINISH.getCode());
            } else {
                //转账尚未到用户卡上
                withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.BANK_HANDLER.getCode());
            }

            Map<String, String> map = new HashMap<>();
            map.put("time", DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND));
            map.put("money", "" + balance.setScale(2, RoundingMode.DOWN));
            withdrawalsRequestReposiroty.save(withdrawalsRequest);
            withdrawalsRecordReposiroty.saveAndFlush(withdrawalsRecord);
            return Result.success(map);

        } else {
            //提现失败
            withdrawalsRecord.setStatus(WithdrawalsRecord.statusEnum.FAIL.getCode());
            BeanUtils.copyProperties(payParam, withdrawalsRecord);
            withdrawalsRecordReposiroty.save(withdrawalsRecord);
            WithdrawalsFails withdrawalsFails = new WithdrawalsFails();
            BeanUtils.copyProperties(payParam, withdrawalsFails);
            withdrawalsFails.setBankReturnData(result.getData().toString());
            withdrawalsFailsReposiroty.save(withdrawalsFails);
            withdrawalsRequestReposiroty.save(withdrawalsRequest);
            return Result.fail("请确认银行卡信息是否正确");
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

        logger.info("没有到账的订单" + reduce);
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
                    wr.setStatus(WithdrawalsRecord.statusEnum.FAIL.getCode());
                    Long userId = wr.getUserId();
                    commonService.setBalance(userId, wr.getMoney(), 2L, wr.getId(), "", "银联退回");
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
                map.put("no", no);
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
            max = "100";
            logger.error("没有找到提现最大值参数，默认为: " + max);
        }

        infoVO.put("withdrawalCount", max);
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        infoVO.put("balance", userAccount.getBalance());
        return Result.success(infoVO);
    }
}
