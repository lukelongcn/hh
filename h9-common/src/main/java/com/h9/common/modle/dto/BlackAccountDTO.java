package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * Created by Gonyb on 2017/11/9.
 */
public class BlackAccountDTO{
    @ApiModelProperty(value = "用户的id数组",required = true)
    @NotNull(message = "请传入用户id")
    private List<Long> userIds;

    @ApiModelProperty(value = "变更的状态 1加入黑名单 2解禁",required = true)
    @NotNull(message = "请传入status")
    @Max(value = 2,message = "请传入正确的状态")
    @Min(value = 1,message = "请传入正确的状态")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
