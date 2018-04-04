package com.h9.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Ln on 2018/3/28.
 */
@Data
@ApiModel
public class AddWinnerUserDTO {
    @ApiModelProperty("手机号")
    @NotNull(message = "phone不能为空")
    private String phone;

    @ApiModelProperty("期数Id")
    @NotNull(message = "activityId 不能为空")
    private Long activityId;

    @ApiModelProperty("金额")
    @NotNull(message = "money不能为空")
    private BigDecimal money;
}
