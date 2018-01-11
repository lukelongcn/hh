package com.h9.admin.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.hotel.Hotel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections.CollectionUtils;
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
    private List<String> images;

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


    public void setImages(String images) {
        List<String> ims = JSONObject.parseArray(images, String.class);
        this.images = ims;
    }

    public HotelListVO(){}

    public HotelListVO(Hotel hotel){

        BeanUtils.copyProperties(hotel, this);
//        List<String> imagesFromDb = hotel.getImages();
//        if(!CollectionUtils.isEmpty(imagesFromDb)){
//            this.images = imagesFromDb.get(0);
//        }

    }

}
