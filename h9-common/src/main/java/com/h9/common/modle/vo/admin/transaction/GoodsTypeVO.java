package com.h9.common.modle.vo.admin.transaction;

import com.h9.common.db.entity.GoodsType;
import com.h9.common.modle.vo.admin.BasisVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

/**
 * @author: George
 * @date: 2017/11/29 16:38
 */
public class GoodsTypeVO extends BasisVO{

    @ApiModelProperty(value = "id ")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "标识")
    private String code;

    @ApiModelProperty(value = "状态,1:启用,2:禁用")
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "是否是实物，0:否，1:是")
    private Integer reality;

    @ApiModelProperty(value = "是否是实物描述")
    private String realityDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Integer getReality() {
        return reality;
    }

    public void setReality(Integer reality) {
        this.reality = reality;
    }

    public String getRealityDesc() {
        return realityDesc;
    }

    public void setRealityDesc(String realityDesc) {
        this.realityDesc = realityDesc;
    }

    public GoodsTypeVO() {
    }

    public GoodsTypeVO(GoodsType goodsType) {
        BeanUtils.copyProperties(goodsType,this);
        this.statusDesc = GoodsType.StatusEnum.getNameById(goodsType.getStatus());
        this.realityDesc = GoodsType.RealityEnum.getNameById(goodsType.getReality());
    }
}
