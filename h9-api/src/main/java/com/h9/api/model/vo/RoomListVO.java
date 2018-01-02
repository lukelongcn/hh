package com.h9.api.model.vo;

import com.h9.common.db.entity.HotelRoomType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/2.
 */
@Data
public class RoomListVO {

    private Long id;

    private String typeName;

    private BigDecimal originalPrice;

    private BigDecimal realPrice;

    private String include;

    private Integer status;

    private String canCancel;

    public RoomListVO() {
    }

    public RoomListVO(HotelRoomType roomType) {
        BeanUtils.copyProperties(roomType, this);
        this.setCanCancel("不可取消");
    }
}
