package com.h9.admin.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: George
 * @date: 2017/11/8 11:26
 */
public class LotteryFlowDTO {
    @ApiModelProperty(value = "兑奖码")
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "中奖状态")
    private String status;
}
