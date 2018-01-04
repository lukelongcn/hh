package com.h9.api.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
public class HotelOrderDetail {

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

    private Integer payMethod;

    private Integer orderStatus;

    private String PayMoney4JiuYuan;

    private String PayMoney4Wechat;

    private String createTime;
}
