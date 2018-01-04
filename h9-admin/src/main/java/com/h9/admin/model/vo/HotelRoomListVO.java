package com.h9.admin.model.vo;

import com.h9.common.db.entity.hotel.HotelRoomType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
public class HotelRoomListVO {

    private Long id;

    private String roomName;
    private String typeName;

    private BigDecimal originalPrice;

    private BigDecimal realPrice;

    private String include;

    private String bedSize;

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
