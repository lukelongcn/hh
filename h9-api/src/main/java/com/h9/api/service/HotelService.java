package com.h9.api.service;

import com.h9.api.model.dto.AddHotelOrderDTO;
import com.h9.api.model.dto.HotelPayDTO;
import com.h9.api.model.vo.HotelDetailVO;
import com.h9.api.model.vo.HotelListVO;
import com.h9.api.model.vo.HotelOrderListVO;
import com.h9.api.model.vo.HotelOrderPayVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.hotel.HotelRoomType;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.HotelOrderRepository;
import com.h9.common.db.repo.HotelRepository;
import com.h9.common.db.repo.HotelRoomTypeRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.utils.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public Result detail(Long hotelId) {
        Hotel hotel = hotelRepository.findOne(hotelId);

        if (hotel == null) return Result.fail("酒店不存在");

        HotelRoomType type = new HotelRoomType().setStatus(1);
        Example<HotelRoomType> of = Example.of(type);

        List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeRepository.findAll(of);

        if (CollectionUtils.isNotEmpty(hotelRoomTypeList)) {
            return Result.success(new HotelDetailVO(hotel, hotelRoomTypeList));
        }
        return Result.success(new HotelDetailVO(hotel, null));
    }

    public Result hotelList(String city, String queryKey,int page,int limit) {

        if (StringUtils.isNotBlank(queryKey)) {
            PageRequest pageRequest = hotelRepository.pageRequest(page, limit);
            Page<Hotel> hotelPage = hotelRepository.findByCityAndHotelName(city, "%" + queryKey + "%",pageRequest);
            if (CollectionUtils.isNotEmpty(hotelPage.getContent())) {
                PageResult<HotelListVO> pageResult = new PageResult<>(hotelPage).result2Result(el -> new HotelListVO(el));
                return Result.success(pageResult);
            } else {
                return Result.fail("没有找到此类酒店");
            }
        }

        return Result.fail("请选择地点/酒店");
    }

    public Result addOrder(AddHotelOrderDTO addHotelOrderDTO, Long userId) {
        HotelRoomType hotelRoomType = hotelRoomTypeRepository.findOne(addHotelOrderDTO.getRoomTypeId());
        if (hotelRoomType == null) {
            return Result.fail("此类房间不存在");
        }

        HotelOrder hotelOrder = initHotelOrder(addHotelOrderDTO, hotelRoomType,userId);

        BigDecimal totalMoney = calcOrderTotalMoney(hotelOrder, hotelRoomType);
        hotelOrder = hotelOrderRepository.saveAndFlush(hotelOrder);

        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        return Result.success(new HotelOrderPayVO()
                .setBalance(MoneyUtils.formatMoney(userAccount.getBalance()))
                .setOrderMoney(MoneyUtils.formatMoney(totalMoney))
                .setTips("请在23:59之前完成支付，过期将自动取消订单")
                .setHotelOrderId(hotelOrder.getId())
        );
    }

    /**
     * description: 订算订单总金额
     */
    private BigDecimal calcOrderTotalMoney(HotelOrder hotelOrder, HotelRoomType hotelRoomType) {
        Integer roomCount = hotelOrder.getRoomCount();
        BigDecimal realPrice = hotelRoomType.getRealPrice();
        BigDecimal totalMoney = realPrice.multiply(new BigDecimal(roomCount)).multiply(new BigDecimal(hotelRoomType.getHotel().getDiscount()));
        return totalMoney;
    }

    /**
     * description: 初始化订单
     */
    public HotelOrder initHotelOrder(AddHotelOrderDTO addHotelOrderDTO, HotelRoomType hotelRoomType, Long userId) {

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
                .setUser_id(userId);
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

    /**
     * description: 支付订单
     */
    public Result payOrder(HotelPayDTO hotelPayDTO) {

        Integer payMethod = hotelPayDTO.getPayMethod();
        HotelOrder.PayMethodEnum payMethodEnum = HotelOrder.PayMethodEnum.findByCode(payMethod);

        if (payMethodEnum == null) return Result.fail("不支持的支付方式");

        HotelOrder hotelOrder = hotelOrderRepository.findOne(hotelPayDTO.getHotelOrderId());
        if (hotelOrder == null) return Result.fail("订单不存在");


        if (hotelOrder.getOrderStatus() != HotelOrder.OrderStatusEnum.NOT_PAID.getCode()) {
            return Result.fail("不能进行此操作");
        }

        hotelOrder.setPayMethod(payMethod);
        hotelOrder.setOrderStatus(WAIT_ENSURE.getCode());
        hotelOrderRepository.save(hotelOrder);

        return Result.success("支付成功");
    }

    public Result orderList(Integer type, Integer page, Integer limit) {

        PageRequest pageRequest = hotelOrderRepository.pageRequest(page, limit);
        Page<HotelOrder> hotelOrderPage = null;

        if (type == 1) {
            hotelOrderPage = hotelOrderRepository.findAll(pageRequest);
        } else {
            Collection<Integer> status = type2HotelOrderStatus(type);
            hotelOrderPage = hotelOrderRepository.findByOrderStatusIn(status, pageRequest);
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
        if(hotelOrder == null) return Result.fail("订单不存在");

        if(!hotelOrder.getUser_id().equals(userId)) return Result.fail("无权查看");

        return null;
    }

    public Result hotelCity() {

        long start = System.currentTimeMillis();
//        List<Hotel> hotelList = hotelRepository.findAllHotelCity();
        List<String> hotelList = hotelRepository.findAllHotelCity();
        long end = System.currentTimeMillis();
        logger.info("consumer time : "+(end - start)/1000F);
//        List<String> cityList = hotelList.stream().map(el -> el.getCity()).collect(Collectors.toList());
        return Result.success(hotelList);
    }
}
