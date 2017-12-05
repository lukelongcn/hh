package com.h9.common.modle.dto.transaction;

import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/12/5 14:10
 */
public class OrderDTO extends PageDTO{
    @ApiModelProperty(value = "券号")
    private String no;

    @ApiModelProperty(value = "批次")
    private String batchNo;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "状态,1:正常，2:已使用,3 禁用")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
