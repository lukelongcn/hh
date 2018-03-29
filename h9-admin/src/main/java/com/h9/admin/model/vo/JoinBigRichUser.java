package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ln on 2018/3/28.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class JoinBigRichUser {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("手机号")
    private String phone = "";
    @ApiModelProperty("妮称")
    private String nickName= "";
    @ApiModelProperty("金额")
    private String money= "0.00";
    @ApiModelProperty("期号")
    private String activity_number= "";
}
