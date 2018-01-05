package com.h9.admin.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.hotel.Hotel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="酒店列表")
public class HotelListVO {


    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("图片")
    private String images;

    @ApiModelProperty("酒店名")
    private String hotelName;

    @ApiModelProperty("房间数")
    private Integer roomCount;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("评分")
    private Float grade;

    @ApiModelProperty("酒店预订电话")
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
