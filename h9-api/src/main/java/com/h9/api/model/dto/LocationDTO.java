package com.h9.api.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/25
 */
@Data
public class LocationDTO {
    // 经度
    @NotNull(message = "经度不能为空")
    private double longitude;

    // 纬度
    @NotNull(message = "纬度不能为空")
    private double latitude;
}
