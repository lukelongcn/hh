package com.h9.store.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.common.ServiceException;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.entity.order.*;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserCoupon;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.StorePayDTO;
import com.h9.common.utils.MoneyUtils;
import com.h9.store.modle.dto.ConvertGoodsDTO;
import com.h9.store.modle.vo.GoodsDetailVO;
import com.h9.store.modle.vo.GoodsListVO;
import com.h9.store.modle.vo.PayResultVO;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2017/11/20.
 */

@Service
public class GoodService {

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;
    @Resource
    private OrderService orderService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private ConfigService configService;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private AddressRepository addressRepository;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;
    @Resource
    private PayInfoRepository payInfoRepository;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private OrdersLotteryActivityRepository ordersLotteryActivityRepository;
    @Resource
    private UserCouponsRepository userCouponsRepository;


    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * description: 减少商品库 -1
     */
    @SuppressWarnings("Duplicates")
    public Result changeStock(Long goodsId) {

        Goods goods = goodsReposiroty.findOne(goodsId);
        if (goods == null) return Result.fail("商品不存在");

        int stock = goods.getStock();
        if (stock <= 0) {
            return Result.fail("库存不足");
        }

        goods.setStock(--stock);
        if (stock <= 0) {
            goods.setStatus(2);
        }
        goodsReposiroty.save(goods);
        return Result.success();
    }

    /**
     * description:
     *
     * @param count 要减少的库存
     */
    @SuppressWarnings("Duplicates")
    public Result changeStock(Long goodsId, int count) {

        Goods goods = goodsReposiroty.findOne(goodsId);
        if (goods == null) return Result.fail("商品不存在");

        int stock = goods.getStock();
        if (stock < count) {
            return Result.fail("库存不足");
        }

        stock -= count;
        goods.setStock(stock);
        if (stock <= 0) {
            goods.setStatus(2);
        }
        goodsReposiroty.save(goods);
        return Result.success();
    }

    public Result changeStock(Goods goods) {
        return changeStock(goods.getId());
    }

    public Result changeStock(Goods goods, int count) {
        return changeStock(goods.getId(), count);
    }


    /**
     * description:
     *
     * @see GoodsType.GoodsTypeEnum 商品类别
     * 商品类型 1今日新品 2日常家居 3食品饮料 4 所有商品
     * <p>
     * MOBILE_RECHARGE("mobile_recharge","手机卡"),
     * DIDI_CARD("didi_card", "滴滴卡"),
     * MATERIAL("material","实物"),
     * FOODS("foods", "食物，饮料"),
     * EVERYDAY_GOODS("everyday_goods", "日常家居"),
     * VB("vb", "V币");
     */
    public Result goodsList(String type, int page, int size) {
        if (StringUtils.isEmpty(type)) {
            type = "o_all";
        }
        GoodsType byCode = goodsTypeReposiroty.findByCode(type);
        if (byCode != null) {
            return goodsPageQuery(type, page, size);
        }
        switch (type) {
            case "o_todayNew":
                return todayNewGoods();
            case "o_all":
                return goodsPageQuery(page, size);
            default:
                return Result.fail("请升级到最新版本");
        }
    }

    /**
     * description: 分页查询指定goodsType的商品列表
     */
    public Result goodsPageQuery(String code, Integer page, Integer size) {

        if (StringUtils.isBlank(code)) return Result.fail("不存在此类型商品");

        Page<Goods> pageObj = goodsReposiroty.findStoreGoods(code, goodsReposiroty.pageRequest(page, size));
        PageResult<Goods> pageResult = new PageResult(pageObj);

        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }

    /**
     * description: 分页查询所有商品
     */
    public Result goodsPageQuery(int page, int size) {

        Page<Goods> pageObj = goodsReposiroty.findStoreGoods(goodsReposiroty.pageRequest(page, size));
        PageResult<Goods> pageResult = new PageResult(pageObj);

        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }

    /**
     * description: 今日新品,取更新时间最近5条的
     */
    public Result todayNewGoods() {
        PageRequest pageRequest = goodsReposiroty.pageRequest(0, 5);
        Page<Goods> pageObj = goodsReposiroty.findLastUpdate(pageRequest);
        PageResult<Goods> pageResult = new PageResult(pageObj);
        PageResult<GoodsListVO> goodsListVOPageResult = pageResult.result2Result(GoodsListVO::new);
        goodsListVOPageResult.setHasNext(false);
        return Result.success(goodsListVOPageResult);
    }


    public Result goodsDetail(Long id, Long userId) {

        Goods goods = goodsReposiroty.findOne(id);
        if (goods == null) {
            return Result.fail("商品不存在");
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        GoodsDetailVO vo = GoodsDetailVO.builder()
                .id(goods.getId())
                .img(goods.getImg())
                .desc(goods.getDescription())
                .price(MoneyUtils.formatMoney(goods.getRealPrice()))
                .name(goods.getName())
                .unit(goods.getUnit())
                .tip("*兑换商品和活动均与设备生产商Apple Inc无关。")
                .stock(goods.getStock())
                .balance(MoneyUtils.formatMoney(userAccount.getBalance()))
                .build();

        List<UserCoupon> listCoupon = userCouponsRepository.findByUserId(userId);
        listCoupon.forEach(userCoupon -> {
            if (userCoupon.getCouponId().getGoodsId().equals(id)) {
                vo.setUserCoupons("已选一张，省￥" + goods.getPrice());
                vo.setUserCouponsId(userCoupon.getId());
            }
        });
        return Result.success(vo);
    }

    @Transactional
    public Result convertGoods(ConvertGoodsDTO convertGoodsDTO, Long userId) throws ServiceException {
        Long addressId = convertGoodsDTO.getAddressId();
        Address address = addressRepository.findOne(addressId);

        if (address == null) return Result.fail("地址不存在");


        Integer count = convertGoodsDTO.getCount();
        Goods goods = goodsReposiroty.findOne(convertGoodsDTO.getGoodsId());
        if (goods == null) return Result.fail("商品不存在");
        BigDecimal goodsPrice = goods.getRealPrice().multiply(new BigDecimal(convertGoodsDTO.getCount()));

        User user = userRepository.findOne(userId);
        Long addressUserId = address.getUserId();

        if (!addressUserId.equals(userId)) return Result.fail("无效的地址");


        //单独判断下余额是否 足够
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        BigDecimal balance = userAccount.getBalance();
        if (convertGoodsDTO.getPayMethod() == 1 && balance.compareTo(goods.getRealPrice().multiply(new BigDecimal(convertGoodsDTO.getCount()))) < 0) {
            return Result.fail("余额不足");
        }

        String code = goods.getGoodsType().getCode();

        Orders order = orderService.initOrder(goodsPrice, address.getPhone()
                , Orders.orderTypeEnum.MATERIAL_GOODS.getCode() + ""
                , "徽酒"
                , user
                , code
                , address.getName());

        order.setAddressId(addressId);
        order.setUserAddres(address.getProvince() + address.getCity() + address.getDistict() + address.getAddress());
        order.setOrderFrom(1);
        order.setStatus(Orders.statusEnum.CANCEL.getCode());
        ordersRepository.saveAndFlush(order);
        int payMethod = convertGoodsDTO.getPayMethod();
        //订单项
        OrderItems orderItems = new OrderItems(goods, count, order);
        ordersRepository.saveAndFlush(order);
        orderItems.setOrders(order);
        orderItemReposiroty.save(orderItems);

        if (payMethod == Orders.PayMethodEnum.WX_PAY.getCode()) {
            // 微信支付
            return getPayInfo(order.getId(), goodsPrice, userId, convertGoodsDTO.getPayPlatform(), count, goods);
        } else {
            //余额支付
            Result result = changeStock(goods, count);
            if (result.getCode() == 1) {
                return result;
            }
            Result balancePayResult = balancePay(order, userId, goods, goodsPrice, count);
            if (balancePayResult.getCode() == 1) {
                joinBigRich(order);
            }
            return balancePayResult;
        }


    }

    /**
     * 参与大富贵活动
     *
     * @param orders 参与的 订单
     * @return
     */
    @SuppressWarnings("Duplicates")
    public Orders joinBigRich(Orders orders) {
        int orderFrom = orders.getOrderFrom();
        if (orderFrom == 2) {
            return orders;
        }
        Date createTime = orders.getCreateTime();
        User user = userRepository.findOne(orders.getUser().getId());
        OrdersLotteryActivity lotteryTime = ordersLotteryActivityRepository.findAllTime(createTime);
        if (lotteryTime != null) {
            orders.setOrdersLotteryId(lotteryTime.getId());
            user.setLotteryChance(user.getLotteryChance()+1);
            logger.info("订单号 " + orders.getId() + " 参与大富贵活动成功 活动id " + lotteryTime.getId());
            ordersRepository.saveAndFlush(orders);
            userRepository.save(user);
        }
        ordersRepository.saveAndFlush(orders);
        return orders;
    }

    private Result balancePay(Orders order, Long userId, Goods goods, BigDecimal goodsPrice, Integer count) {
        String balanceFlowType = configService.getValueFromMap("balanceFlowType", "12");
        Result payResult = commonService.setBalance(userId, goodsPrice.negate(), 12L, order.getId(), "", balanceFlowType);
        if (!payResult.isSuccess()) {
            throw new ServiceException(payResult);
        }
        order.setStatus(Orders.statusEnum.WAIT_SEND.getCode());
        order.setPayStatus(Orders.PayStatusEnum.PAID.getCode());

        Map<String, String> mapVo = new HashMap<>();
        mapVo.put("price", MoneyUtils.formatMoney(goodsPrice));
        mapVo.put("goodsName", goods.getName() + "*" + count);
        if (order.getOrdersLotteryId() != null) {
        mapVo.put("activityName", "1号大富贵");
        mapVo.put("lotteryChance", "获得1次抽奖机会");
        }
        return Result.success(mapVo);
    }

    @Value("${path.app.wechat_host}")
    private String wxHost;
    @Resource
    private RedisBean redisBean;

    private Result getPayInfo(Long orderId, BigDecimal money, Long userId, String payPlatform, Integer count, Goods goods) {
        String url = wxHost + "/h9/api/pay/payInfo";
        StorePayDTO payDTO = new StorePayDTO(orderId, money, userId, payPlatform);
        try {
            logger.info("access url : " + url);
            Result result = restTemplate.postForObject(url, payDTO, Result.class);
            if (result.getCode() == 1) {
                return Result.fail("支付异常");
            }
            Orders order = ordersRepository.findOne(orderId);
            String payInfoId = redisBean.getStringValue("orderId:" + orderId);
            order.setPayInfoId(Long.valueOf(payInfoId));
            ordersRepository.saveAndFlush(order);
            Object data = result.getData();
            PayResultVO payResultVO = JSONObject.parseObject(JSONObject.toJSONString(data), PayResultVO.class);
            Map<String, Object> mapVO = new HashMap<>();
            mapVO.put("price", MoneyUtils.formatMoney(money));
            mapVO.put("goodsName", goods.getName() + "*" + count);
            mapVO.put("wxPayInfo", payResultVO.getWxPayInfo());
            order = ordersRepository.findOne(orderId);
            if (order.getOrdersLotteryId() != null) {
                mapVO.put("activityName", "1号大富贵");
                mapVO.put("lotteryChance", "获得1次抽奖机会");
            }
            return Result.success(mapVO);
        } catch (RestClientException e) {
            logger.info("调用 出现异常");
            logger.info(e.getMessage(), e);
            return Result.fail("获取支付信息失败，请稍后再试");
        }
    }

}
