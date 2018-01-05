package com.h9.admin.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by itservice on 2018/1/5.
 */
@Data
@Accessors(chain = true)
public class HotelOrderSearchDTO {

    private Long hotelOrderId;

    private String phone;

    private Date startDate;

    private Date endDate;

    private Integer status;
}
