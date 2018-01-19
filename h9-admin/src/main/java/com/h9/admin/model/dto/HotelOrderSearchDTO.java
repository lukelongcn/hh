package com.h9.admin.model.dto;

import com.h9.common.utils.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Calendar;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {

        if(startDate != null){
            this.startDate = new Date(startDate);
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        if(endDate != null){
            this.endDate = new Date(endDate);
        }
    }

    public static void main(String[] args) {
        Date date = DateUtil.getDate(new Date(), -1, Calendar.YEAR);
        Date date1 = DateUtil.getDate(new Date(), 1, Calendar.YEAR);
        System.out.println(date.getTime());
        System.out.println(date1.getTime());

    }
}
