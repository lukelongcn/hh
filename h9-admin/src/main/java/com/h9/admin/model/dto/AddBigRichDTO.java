package com.h9.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Ln on 2018/3/28.
 */
@Accessors(chain = true)
@ApiModel(value = "新增参数")
@Data
public class AddBigRichDTO {

    @ApiModelProperty("期数Id,编辑 不用填")
    private Long id;

    @NotNull(message = "开始时间不能为空")
    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    @NotNull(message = "结束时间不能为空")
    private Long endTime;

    @NotNull(message = "请选择状态")
    @ApiModelProperty("启用 1/禁用 0")
    @Range(min = 0,max = 1,message = "状态 1 - 0之间")
    private Integer status;

    @NotNull(message = "开奖时间不能为空")
    @ApiModelProperty("开奖时间")
    private Long startLotteryTime;


}
