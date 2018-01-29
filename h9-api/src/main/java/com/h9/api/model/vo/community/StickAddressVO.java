package com.h9.api.model.vo.community;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/25
 */
@Data
public class StickAddressVO {
    // 经度
    private double longitude;

    // 维度
    private double latitude;

    // 地址
    private String address;

    // 省
    private String province;

    // 市
    private String city;

    // 区
    private String district;
}
