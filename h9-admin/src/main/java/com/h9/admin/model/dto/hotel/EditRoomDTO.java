package com.h9.admin.model.dto.hotel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "编辑房间参数")
public class EditRoomDTO {


    private Long id;

    @ApiModelProperty("房间名")
    @NotNull(message = "请添写房间名")
    private String roomName;

    @ApiModelProperty("房间类型名")
    @NotNull(message = "")
    private String typeName;

    @ApiModelProperty("房间尺寸")
    @NotNull(message = "请添写房间尺寸")
    private String bedSize;

    @ApiModelProperty("是否含早")
    @NotNull(message = "请添写房是否含早")
    private String include;

    @ApiModelProperty("图片")
    @Size(max = 9,message = "图片不得超过九张")
    @NotNull(message = "请添加图片")
    private List<String> images;

    @ApiModelProperty("原价")
    @NotNull(message = "请输入原价")
    private BigDecimal originalPrice;

    @ApiModelProperty("售价")
    @NotNull(message = "请输入售价")
    private BigDecimal realPrice;

    @ApiModelProperty("酒店Id")
    @NotNull(message = "请添写所属酒店Id")
    private Long hotelId;
}
