package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.GoodsType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/29 16:13
 */
public class GoodsTypeAddDTO {

    @ApiModelProperty(value = "名称",required = true)
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "状态,1:启用,2:禁用",required = true)
    @NotNull(message = "状态不能为空")
    private Integer status = 1;

    @ApiModelProperty(value = "是否允许导入数据，1:否，2:是",required = true)
    @NotNull(message = "是否允许导入数据不能为空")
    private Integer allowImport = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAllowImport() {
        return allowImport;
    }

    public void setAllowImport(Integer allowImport) {
        this.allowImport = allowImport;
    }

    public GoodsType toGoodsType() {
        GoodsType goodsType = new GoodsType();
        BeanUtils.copyProperties(this,goodsType);
        return goodsType;
    }
}
