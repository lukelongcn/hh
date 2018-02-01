package com.h9.api.model.vo;

import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
public class HotelOrderDetailVO {

    private Long id;

    private String hotelName;

    private String hotelAddress;

    private String comeRoomTime;

    private String outRoomTime;

    private String roomTypeName;

    private int stayNightCount;

    private Integer roomCount;

    private String include;

    private String totalMoney;

    private String roomer;

    private String phone;

    private String PayMoney4JiuYuan;

    private String PayMoney4Wechat;

    private String createTime;

    private String statusDesc;

    public HotelOrderDetailVO(){}

    public HotelOrderDetailVO(HotelOrder hotelOrder){
        BeanUtils.copyProperties(hotelOrder, this);

        Integer orderStatus = hotelOrder.getOrderStatus();
        HotelOrder.OrderStatusEnum findEnum = HotelOrder.OrderStatusEnum.findByCode(orderStatus);


        int nightSum = DateUtil.nightSum(hotelOrder.getComeRoomTime(), hotelOrder.getOutRoomTime());
        this.setStatusDesc(findEnum.getDesc());
        this.setCreateTime(DateUtil.formatDate(hotelOrder.getCreateTime(), DateUtil.FormatType.MINUTE))
                .setComeRoomTime(DateUtil.formatDate(hotelOrder.getComeRoomTime(), DateUtil.FormatType.DAY))
                .setOutRoomTime(DateUtil.formatDate(hotelOrder.getOutRoomTime(), DateUtil.FormatType.DAY))
                .setTotalMoney(MoneyUtils.formatMoney(hotelOrder.getTotalMoney()))
                .setPayMoney4JiuYuan(MoneyUtils.formatMoney(hotelOrder.getPayMoney4JiuYuan()))
                .setStayNightCount(nightSum)
                .setRoomTypeName(hotelOrder.getRoomTypeName())
                .setPayMoney4Wechat(MoneyUtils.formatMoney(hotelOrder.getPayMoney4Wechat()));

    }

}
