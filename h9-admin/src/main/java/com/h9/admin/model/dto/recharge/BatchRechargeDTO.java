package com.h9.admin.model.dto.recharge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2018/1/11.
 */
@Data
@Accessors(chain = true)
@ApiModel("导入参数")
public class BatchRechargeDTO {

    @NotNull(message = "批次号不能为空")
    private String batchNo;

    private String remark;

    @NotNull(message = "请传入cacheId")
    private String cacheId;
}
