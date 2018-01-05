package com.h9.api.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.hotel.Hotel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itservice on 2018/1/2.
 */
@Data
@Accessors(chain = true)
public class HotelListVO {

    private List<String> images = new ArrayList<>();

    private String hotelName = "";

    private Float grade;

    private String detailAddress;

    private Float discount;

    private BigDecimal mixConsumer = new BigDecimal(0);

    private Long id;

    public HotelListVO( ) {
    }

    public HotelListVO(Hotel hotel){
        String imgJson = hotel.getImages();
        if (StringUtils.isNotBlank(imgJson)) {
            List<String> images = JSONObject.parseArray(imgJson, String.class);
            this.setImages(images);
        }
        BeanUtils.copyProperties(hotel,this);
    }
}
