package com.h9.admin.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.hotel.HotelRoomType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HotelRoomListVO")
public class HotelRoomListVO {

    private Long id;

    @ApiModelProperty("房间名")
    private String roomName;
    @ApiModelProperty("房间类型名")
    private String typeName;

    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    @ApiModelProperty("售价")
    private BigDecimal realPrice;

    @ApiModelProperty("是否含早")
    private String include;

    @ApiModelProperty("床尺寸")
    private String bedSize;

    @ApiModelProperty("房间状态")
    private Integer status;

    @ApiModelProperty("状态描述")
    private String statusDesc;

    @ApiModelProperty("酒店名称")
    private  String hotelName;

    private List<String> images = new ArrayList<>();


    public HotelRoomListVO( ) {
    }
    public HotelRoomListVO(HotelRoomType roomType) {
        BeanUtils.copyProperties(roomType,this);
        this.setHotelName(roomType.getHotel().getHotelName());

        String jsonImg = roomType.getImage();
        if(StringUtils.isNotBlank(jsonImg)){
            List<String> images = JSONObject.parseArray(jsonImg,String.class);
            this.setImages(images);
        }
        HotelRoomType.Status findStatus = HotelRoomType.Status.findByCode(roomType.getStatus());
        if(findStatus != null){
            this.setStatus(findStatus.getCode());
            this.setStatusDesc(findStatus.getDesc());
        }else{
            this.setStatusDesc("未知状态");
            this.setStatus(-1);
        }
    }

}
