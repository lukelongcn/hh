package com.h9.admin.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.hotel.Hotel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
public class HotelListVO {


    private Long id;

    private String images;

    private String hotelName;

    private Integer roomCount;

    private String detailAddress;

    private Float grade;

    private String hotelPhone;


    public HotelListVO(){}

    public HotelListVO(Hotel hotel){

        BeanUtils.copyProperties(hotel, this);
        String imgJson = hotel.getImages();

        if (StringUtils.isNotBlank(imgJson)) {

            List<String> imgList = JSONObject.parseArray(imgJson, String.class);
            String img = imgList.get(0);
            this.setImages(img);

        }

    }

}
