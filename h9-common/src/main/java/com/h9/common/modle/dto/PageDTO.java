package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: George
 * @date: 2017/11/2 17:55
 */
@ApiModel(value = "分页参数")
public class PageDTO {

    @ApiModelProperty(value = "页码")
    private int pageNumber = 1;
    @ApiModelProperty(value = "页大小")
    private int pageSize = 20;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
