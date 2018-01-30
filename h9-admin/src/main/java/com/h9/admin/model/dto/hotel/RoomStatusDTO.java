package com.h9.admin.model.dto.hotel;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2018/1/30.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoomStatusDTO {
    @NotNull(message = "请传入id")
    private Long hotelOrderId;
    @NotNull(message = "传入status")
    private Integer status;
}
