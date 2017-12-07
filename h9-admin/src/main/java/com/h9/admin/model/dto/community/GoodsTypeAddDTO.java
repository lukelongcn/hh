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

    @ApiModelProperty(value = "标识",required = true)
    @NotBlank(message = "标识不能为空")
    private String code;

    @ApiModelProperty(value = "状态,1:启用,2:禁用",required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "是否允许导入数据，1:否，2:是",required = true)
    @NotNull(message = "是否允许导入数据不能为空")
    private Integer allowImport;

    @ApiModelProperty(value = " 是否是实物，0:否，1:是",required = true)
    @NotNull(message = "是否是实物不能为空")
    private Integer reality;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getReality() {
        return reality;
    }

    public void setReality(Integer reality) {
        this.reality = reality;
    }

    public GoodsType toGoodsType() {
        GoodsType goodsType = new GoodsType();
        BeanUtils.copyProperties(this,goodsType);
        return goodsType;
    }
}
