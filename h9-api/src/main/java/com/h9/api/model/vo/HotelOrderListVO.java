package com.h9.api.model.vo;


import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.db.entity.hotel.HotelRoomType;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class HotelOrderListVO {

    private String hotelName;

    private String status;

    private String comeRoomTime;

    private String outRoomTime;

    private String orderMoney;

    private int stayNightCount;

    private int roomCount;

    private String roomTypeName;

    private Long id;

    private boolean showPayBtn = false;

    public HotelOrderListVO() {
    }

    public HotelOrderListVO(HotelOrder hotelOrder) {
        HotelRoomType roomType = hotelOrder.getHotelRoomType();
        Integer orderStatus = hotelOrder.getOrderStatus();
        HotelOrder.OrderStatusEnum findEnumStatus = HotelOrder.OrderStatusEnum.findByCode(orderStatus);
        if (findEnumStatus != null) {
            this.setStatus(findEnumStatus.getDesc());
            if (findEnumStatus.getCode() == HotelOrder.OrderStatusEnum.NOT_PAID.getCode()) {
                this.setShowPayBtn(true);
            }
        }


        Date comeRoomTime = hotelOrder.getComeRoomTime();
        Date outRoomTime = hotelOrder.getOutRoomTime();

        int dayChange = DateUtil.differentDays(comeRoomTime, outRoomTime);
        this.setHotelName(roomType.getHotel().getHotelName())
                .setComeRoomTime(DateUtil.formatDate(hotelOrder.getComeRoomTime(), DateUtil.FormatType.DAY))
                .setOutRoomTime(DateUtil.formatDate(hotelOrder.getOutRoomTime(), DateUtil.FormatType.DAY))
                .setStayNightCount(dayChange)
                .setRoomCount(hotelOrder.getRoomCount())
                .setRoomTypeName(hotelOrder.getRoomTypeName())
                .setOrderMoney(MoneyUtils.formatMoney(hotelOrder.getTotalMoney()))
                .setId(hotelOrder.getId());
    }

}
