package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.AddHotelOrderDTO;
import com.h9.api.model.dto.HotelPayDTO;
import com.h9.api.model.dto.OrderDTO;
import com.h9.api.model.vo.*;
import com.h9.api.provider.PayProvider;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.hotel.HotelRoomType;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.h9.common.db.entity.account.BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY;
import static com.h9.common.db.entity.hotel.HotelOrder.OrderStatusEnum.NOT_PAID;
import static com.h9.common.db.entity.hotel.HotelOrder.OrderStatusEnum.REFUND_MONEY;
import static com.h9.common.db.entity.hotel.HotelOrder.OrderStatusEnum.WAIT_ENSURE;

/**
 * Created by itservice on 2018/1/2.
 */
@Service
public class HotelService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private HotelRepository hotelRepository;

    @Resource
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Resource
    private ConfigService configService;

    @Resource
    private HotelOrderRepository hotelOrderRepository;

    @Resource
    private UserAccountRepository userAccountRepository;

    @Value("${path.app.wechat_host}")
    private String wechatHostUrl;

    public Result detail(Long hotelId) {
        Hotel hotel = hotelRepository.findOne(hotelId);

        if (hotel == null) return Result.fail("酒店不存在");

        List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeRepository.findAll(Example.of(new HotelRoomType().setStatus(1)));

        if (CollectionUtils.isNotEmpty(hotelRoomTypeList)) {
            return Result.success(new HotelDetailVO(hotel, hotelRoomTypeList, wechatHostUrl));
        }
        return Result.success(new HotelDetailVO(hotel, null, wechatHostUrl));
    }

    public Result hotelList(String city, String queryKey, int page, int limit) {

        if (StringUtils.isNotBlank(queryKey)) {
            PageRequest pageRequest = hotelRepository.pageRequest(page, limit);
            Page<Hotel> hotelPage = hotelRepository.findByCityAndHotelName(city, "%" + queryKey + "%", pageRequest);
            if (CollectionUtils.isNotEmpty(hotelPage.getContent())) {
                PageResult<HotelListVO> pageResult = new PageResult<>(hotelPage).result2Result(el -> new HotelListVO(el));
                return Result.success(pageResult);
            } else {
                return Result.fail("没有找到此类酒店");
            }
        } else {
            return Result.success(hotelRepository.findAll(page, limit).map(HotelListVO::new));
        }

    }

    public Result addOrder(AddHotelOrderDTO addHotelOrderDTO, Long userId) {
        HotelRoomType hotelRoomType = hotelRoomTypeRepository.findOne(addHotelOrderDTO.getRoomTypeId());
        if (hotelRoomType == null) {
            return Result.fail("此类房间不存在");
        }

        Date comeRoomTime = new Date();
        Hotel hotel = hotelRoomType.getHotel();
        Date startReserveTime = hotel.getStartReserveTime();
        Date endReserveTime = hotel.getEndReserveTime();

        if (comeRoomTime.getTime() < startReserveTime.getTime() || comeRoomTime.getTime() > endReserveTime.getTime()) {
            return Result.fail("非可用时段不可预订");
        }

        HotelOrder hotelOrder = initHotelOrder(addHotelOrderDTO, hotelRoomType, userId);

        hotelOrder = hotelOrderRepository.saveAndFlush(hotelOrder);

        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        return Result.success(new HotelOrderPayVO(hotelOrder, userAccount, hotelOrder.getTotalMoney()));
    }

    /**
     * description: 订算订单总金额
     */
    private BigDecimal calcOrderTotalMoney(HotelOrder hotelOrder, HotelRoomType hotelRoomType) {
        Integer roomCount = hotelOrder.getRoomCount();
        BigDecimal realPrice = hotelRoomType.getRealPrice();
        BigDecimal totalMoney = realPrice.multiply(new BigDecimal(roomCount));
        return totalMoney;
    }

    /**
     * description: 初始化订单
     */
    public HotelOrder initHotelOrder(AddHotelOrderDTO addHotelOrderDTO, HotelRoomType hotelRoomType, Long userId) {

        Integer roomCount = addHotelOrderDTO.getRoomCount();
        BigDecimal realPrice = hotelRoomType.getRealPrice();
        BigDecimal totalMoney = realPrice.multiply(new BigDecimal(roomCount));

        return new HotelOrder()
                .setOrderStatus(HotelOrder.OrderStatusEnum.NOT_PAID.getCode())
                .setComeRoomTime(addHotelOrderDTO.getComeRoomTime())
                .setOutRoomTime(addHotelOrderDTO.getOutRoomTime())
                .setHotelAddress(hotelRoomType.getHotel().getDetailAddress())
                .setHotelName(hotelRoomType.getHotel().getHotelName())
                .setPhone(addHotelOrderDTO.getPhone())
                .setRoomer(addHotelOrderDTO.getStayRoomer())
                .setRoomTypeName(hotelRoomType.getTypeName())
                .setRoomCount(addHotelOrderDTO.getRoomCount())
                .setHotel(hotelRoomType.getHotel())
                .setHotelRoomType(hotelRoomType)
                .setInclude(hotelRoomType.getInclude())
                .setTotalMoney(totalMoney)
                .setRemarks(addHotelOrderDTO.getRemark())
                .setUserId(userId)
                .setRoomStyle(addHotelOrderDTO.getRoomStyle())
                .setKeepTime(addHotelOrderDTO.getKeepTime());
    }

    /**
     * description:
     * <p>
     * 18:00之前
     * 20:00之前
     * 23:00之前
     * 次日凌晨06:00之前
     */
    public Result hotelOptions() {

        // ["18:00之前","20:00之前","23:00之前","次日凌晨06:00之前"]
        // ["无","有烟房","无烟房"]
        // ["1间","2间","3间","4间","5间","6间"]
        String keepTimeOption = "keepTimeOptions";
        String roomType = "roomTypeOptions";
        String roomCount = "roomCountOptions";

        List<String> keepTimeOptions = configService.getStringListConfig(keepTimeOption);
        List<String> roomTypeOptions = configService.getStringListConfig(roomType);
        List<String> roomCountOptions = configService.getStringListConfig(roomCount);

        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("keepTimeOptions", keepTimeOptions);
        mapResult.put("roomTypeOptions", roomTypeOptions);
        mapResult.put("roomCountOptions", roomCountOptions);

        return Result.success(mapResult);
    }

    @Resource
    private CommonService commonService;

    @Resource
    private PayProvider payProvider;
    @Resource
    private UserRepository userRepository;

    /**
     * description: 支付订单
     */
    @Transactional
    public Result payOrder(HotelPayDTO hotelPayDTO, Long userId) {

        Integer payMethod = hotelPayDTO.getPayMethod();
        if (payMethod == null) {
            return Result.fail("请选择支付方式");
        }

        HotelOrder.PayMethodEnum payMethodEnum = HotelOrder.PayMethodEnum.findByCode(payMethod);
        if (payMethodEnum == null) return Result.fail("请选择支付方式");

        HotelOrder hotelOrder = hotelOrderRepository.findOne(hotelPayDTO.getHotelOrderId());
        if (hotelOrder == null) return Result.fail("订单不存在");

        if (hotelOrder.getOrderStatus() != HotelOrder.OrderStatusEnum.NOT_PAID.getCode()) {
            return Result.fail("订单已支付");
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        User user = userRepository.findOne(userId);

        String payPlatform = hotelPayDTO.getPayPlatform();
        OrderDTO.PayMethodEnum findPayPlatformEnum = OrderDTO.PayMethodEnum.getByValue(payPlatform);
        if (findPayPlatformEnum == null) return Result.fail("请传入支付平台类型，如，'wx'(微信APP）'wxjs'(公众号)");
        Result result = null;
        if (payMethodEnum == HotelOrder.PayMethodEnum.BALANCE_PAY) {
            result = balancePay(hotelOrder, user, userAccount);
        } else if (payMethodEnum == HotelOrder.PayMethodEnum.WECHAT_PAY) {
            result = getWXPayInfo(hotelOrder, hotelOrder.getTotalMoney(), user, findPayPlatformEnum.getKey());
        } else if (payMethodEnum == HotelOrder.PayMethodEnum.MIXED_PAY) {
            result = mixedPay(hotelOrder, userAccount, user, findPayPlatformEnum.getKey());
        } else {
            return Result.fail("不支持的支付方式");
        }
        hotelOrder.setPayMethod(payMethod);
        hotelOrderRepository.save(hotelOrder);
        return result;
    }


    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PayInfoRepository payInfoRepository;

    private Result getWXPayInfo(HotelOrder hotelOrder, BigDecimal payMoney, User user, int payPlatform) {
        payProvider.initConfig();
        PayInfo payInfo = new PayInfo(payMoney, hotelOrder.getId(), PayInfo.OrderTypeEnum.HOTEL.getId(), null, 1);
        payInfo = payInfoRepository.saveAndFlush(payInfo);
        String openId = user.getOpenId();

        if (payPlatform == OrderDTO.PayMethodEnum.WXJS.getKey() && StringUtils.isBlank(openId)) {
            logger.info("支付出错，账号" + user.getId() + " openId为空");
            return Result.fail("支付出错，账号" + user.getId() + " openId为空");
        }
        OrderDTO orderDTO = new OrderDTO(openId, payMoney, payInfo.getId(), payPlatform);

        Result<OrderVo> orderVoResult = null;
        try {
            orderVoResult = payProvider.initOrder(orderDTO);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return Result.fail("获取支付信息失败，请稍后再试");
        }

        if (!orderVoResult.isSuccess()) {
            logger.info("获取支付信息失败，获取结果： " + JSONObject.toJSONString(orderVoResult));
            return orderVoResult;
        }

        OrderVo orderVo = orderVoResult.getData();

        int orderStatus = hotelOrder.getOrderStatus();

        boolean resumePay = false;
        if (orderStatus == HotelOrder.OrderStatusEnum.NOT_PAID.getCode()) {
            resumePay = true;
        }
        if (payPlatform == 3) {
            String pay = payProvider.goPay(orderVo.getPayOrderId(), payInfo.getId());
            PayVO payVO = new PayVO();
            payVO.setPayOrderId(orderVo.getPayOrderId());
            payVO.setPayUrl(pay);
            payVO.setOrderId(hotelOrder.getId());
            //公众号
            PayResultVO vo = new PayResultVO(new PayResultVO.PayResult("1", resumePay), payVO);
            return Result.success(vo);
        } else {
            //APP 支付
            Result prepayResult = payProvider.getPrepayInfo(orderVo);
            PayResultVO vo = new PayResultVO(new PayResultVO.PayResult("1", resumePay), prepayResult.getData());
            return Result.success(prepayResult);
        }
    }

    private Result balancePay(HotelOrder hotelOrder, User user, UserAccount userAccount) {
        BigDecimal balance = userAccount.getBalance();
        if (balance.compareTo(hotelOrder.getTotalMoney()) < 0) {
            return Result.fail("余额不足");
        }
        commonService.setBalance(user.getId(), hotelOrder.getTotalMoney(), BALANCE_PAY.getId(), hotelOrder.getId(), "", BALANCE_PAY.getName());
        hotelOrder.setPayMoney4JiuYuan(hotelOrder.getTotalMoney());
        hotelOrder.setOrderStatus(HotelOrder.OrderStatusEnum.WAIT_ENSURE.getCode());
        PayResultVO vo = new PayResultVO(new PayResultVO.PayResult("", false));
        return Result.success(vo);
    }

    /**
     * description: 混合支付
     */
    private Result mixedPay(HotelOrder hotelOrder, UserAccount userAccount, User user, int key) {
        BigDecimal totalMoney = hotelOrder.getTotalMoney();
        BigDecimal balance = userAccount.getBalance();
        if (balance.compareTo(totalMoney) >= 0) {
            //直接用余额支付完
//            commonService.setBalance(userAccount.getUserId(), hotelOrder.getTotalMoney(), BALANCE_PAY.getId(), hotelOrder.getId(), "", BALANCE_PAY.getName());
//            BigDecimal paidMoney4JiuYuan = hotelOrder.getPayMoney4JiuYuan();
//            paidMoney4JiuYuan = paidMoney4JiuYuan.add(hotelOrder.getTotalMoney());
//            hotelOrder.setPayMoney4JiuYuan(paidMoney4JiuYuan);
            return Result.success(balancePay(hotelOrder, user, userAccount));
        } else {
            // 余额 + 微信支付
//            if (balance.compareTo(new BigDecimal(0)) > 0) {
//                commonService.setBalance(userAccount.getUserId(), balance, BALANCE_PAY.getId(), hotelOrder.getId(), "", BALANCE_PAY.getName());
//                BigDecimal paidMoney4JiuYuan = hotelOrder.getPayMoney4JiuYuan();
//                paidMoney4JiuYuan = paidMoney4JiuYuan.add(balance);
//                hotelOrder.setPayMoney4JiuYuan(paidMoney4JiuYuan);
//            }
            BigDecimal wxPayMoney = totalMoney.subtract(hotelOrder.getPayMoney4JiuYuan());
            balancePay(hotelOrder, user, userAccount);
            return getWXPayInfo(hotelOrder, wxPayMoney, user, key);

        }
    }

    public Result orderList(Long userId, Integer type, Integer page, Integer limit) {

        PageRequest pageRequest = hotelOrderRepository.pageRequest(page, limit);
        Page<HotelOrder> hotelOrderPage = null;

        if (type == 1) {
            hotelOrderPage = hotelOrderRepository.findAllByUserId(userId, pageRequest);
        } else {
            Collection<Integer> statusList = type2HotelOrderStatus(type);
            if (CollectionUtils.isEmpty(statusList)) {
                return Result.fail("请返回正确的type类型");
            }
            hotelOrderPage = hotelOrderRepository.findAllBy(userId, statusList, pageRequest);
        }

        PageResult<Object> pageResult = new PageResult<>(hotelOrderPage).result2Result(el -> new HotelOrderListVO(el));
        return Result.success(pageResult);

    }

    private Collection<Integer> type2HotelOrderStatus(int type) {
        switch (type) {

            case 2:
                //【待确认，预订成功】
                return Arrays.asList(HotelOrder.OrderStatusEnum.SUCCESS.getCode(), WAIT_ENSURE.getCode());
            case 3:

                return Arrays.asList(NOT_PAID.getCode());
            case 4:

                return Arrays.asList(REFUND_MONEY.getCode());
            default:

                return null;
        }
    }

    public Result orderDetail(Long orderId, Long userId) {

        HotelOrder hotelOrder = hotelOrderRepository.findOne(orderId);
        if (hotelOrder == null) return Result.fail("订单不存在");

        if (!hotelOrder.getUserId().equals(userId)) return Result.fail("无权查看");

        return Result.success(new HotelOrderDetailVO(hotelOrder));
    }

    public Result hotelCity() {

        long start = System.currentTimeMillis();
//        List<Hotel> hotelList = hotelRepository.findAllHotelCity();
        List<String> hotelList = hotelRepository.findAllHotelCity();
        long end = System.currentTimeMillis();
        logger.info("consumer time : " + (end - start) / 1000F);
//        List<String> cityList = hotelList.stream().map(el -> el.getCity()).collect(Collectors.toList());
        return Result.success(hotelList);
    }

    public Result<HotelOrder> authOrder(Long hotelOrderId, Long userId) {
        HotelOrder hotelOrder = hotelOrderRepository.findOne(hotelOrderId);
        if (hotelOrder == null) return Result.fail("订单不存在");

        if (!hotelOrder.getUserId().equals(userId)) return Result.fail("无权查看");

        return Result.success(hotelOrder);
    }

    public Result payInfo(Long hotelOrderId, Long userId) {
        Result<HotelOrder> authResult = authOrder(hotelOrderId, userId);
        if (authResult.getCode() == 1) return authResult;

        UserAccount userAccount = userAccountRepository.findOne(userId);

        BigDecimal totalMoeny = calcOrderTotalMoney(authResult.getData(), authResult.getData().getHotelRoomType());
        return Result.success(new HotelOrderPayVO(authResult.getData(), userAccount, totalMoeny));
    }

    public String orderDetail(Long hotelId) {
        Hotel hotel = hotelRepository.findOne(hotelId);
        if (hotel == null) return "酒店不存在";
        return hotel.getHotelInfo();
    }
}
