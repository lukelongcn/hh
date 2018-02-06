package com.h9.api.model.dto;

import com.google.common.collect.BiMap;

import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/19
 */
@Data
public class StickRewardJiuYuanDTO {
    @NotNull(message = "贴子id不能为空")
    private Long stickId;
    // 留言
    private String words;
    @NotNull(message = "打赏金额不能为空")
    private BigDecimal reward = new BigDecimal(0);

    public StickRewardJiuYuanDTO() {
    }
}
