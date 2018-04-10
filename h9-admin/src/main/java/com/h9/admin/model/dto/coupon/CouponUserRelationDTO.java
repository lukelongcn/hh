package com.h9.admin.model.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ln on 2018/4/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponUserRelationDTO {
    private String phone;
    private String count;
    private Long userId;
    private String msg;
}
