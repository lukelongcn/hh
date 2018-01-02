package com.h9.api.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.Hotel;
import com.h9.common.db.entity.HotelRoomType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by itservice on 2018/1/2.
 */
@Getter
@Setter
public class HotelDetailVO {
    private Long id;
    private List<String> images = new ArrayList<>();
    private Float grade;
    private String detailAddress;
    private String tips;
    private List<RoomListVO> roomList = new ArrayList<>();
    private String hotelInfo;

    public HotelDetailVO() {
    }


    public HotelDetailVO(Hotel hotel,List<HotelRoomType> roomType) {

        BeanUtils.copyProperties(hotel, this);

        List<RoomListVO> roomList = roomType.stream().map(el -> new RoomListVO(el)).collect(Collectors.toList());
        this.setRoomList(roomList);

        String imagesJson = hotel.getImages();
        if (StringUtils.isNotBlank(imagesJson)) {
            List<String> img = JSONObject.parseArray(imagesJson, String.class);
            this.setImages(img);
        }

    }
}
