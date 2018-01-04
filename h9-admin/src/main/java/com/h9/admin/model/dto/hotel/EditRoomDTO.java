package com.h9.admin.model.dto.hotel;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
public class EditRoomDTO {


    private Long id;

    @NotNull(message = "请添写房间名")
    private String roomName;
    @NotNull(message = "请添写房间类型名")
    private String typeName;

    @NotNull(message = "请添写房间尺寸")
    private String bedSize;

    @NotNull(message = "请添写房是否含早")
    private String include;

    private List<String> image;

    private BigDecimal originalPrice;

    private BigDecimal realPrice;

    @NotNull(message = "请添写所属酒店Id")
    private Long hotelId;
}
