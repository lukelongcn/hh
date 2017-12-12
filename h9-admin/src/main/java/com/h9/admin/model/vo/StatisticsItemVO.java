package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;


/**
 *  Created by Gonyb on 2017/11/9.
 */
public class StatisticsItemVO {

    @ApiModelProperty(value = "统计的项目名称")
    private String name;

    @ApiModelProperty(value = "统计值")
    private Object value ;

    @ApiModelProperty(value = "项目描述")
    private String desc;

    public StatisticsItemVO() {
    }

    public StatisticsItemVO(String name, Object value, String desc) {
        this.name = name;
        this.value = value;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}