package com.h9.common.modle.vo.admin.transaction;

import com.h9.common.db.entity.GoodsType;
import com.h9.common.modle.vo.admin.BasisVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * @author: George
 * @date: 2017/11/29 16:38
 */
public class GoodsTypeVO extends BasisVO{

    @ApiModelProperty(value = "id ")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "状态,1:启用,2:禁用")
    private Integer status = 1;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

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

    public GoodsTypeVO() {
    }

    public GoodsTypeVO(GoodsType goodsType) {
        BeanUtils.copyProperties(goodsType,this);
    }
}
