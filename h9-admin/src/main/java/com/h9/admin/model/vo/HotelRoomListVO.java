package com.h9.admin.model.vo;

import com.h9.common.db.entity.hotel.HotelRoomType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

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
    private String status;

    public HotelRoomListVO( ) {
    }
    public HotelRoomListVO(HotelRoomType roomType) {
        BeanUtils.copyProperties(roomType,this);

        HotelRoomType.Status findStatus = HotelRoomType.Status.findByCode(roomType.getStatus());
        if(findStatus != null){
            this.setStatus(findStatus.getDesc());
        }
    }

}
