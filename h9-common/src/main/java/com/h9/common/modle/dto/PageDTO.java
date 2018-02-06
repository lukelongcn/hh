package com.h9.common.modle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author: George
 * @date: 2017/11/2 17:55
 */
@Data
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
    
    
    public PageRequest toPageRequest(){
        //从第一页开始
        if(pageNumber<1) pageNumber = 1;
        if(pageSize < 1) pageSize = 20;
//       防止请求页数过大，整垮服务器
        if(pageSize>100) pageSize = 100;
        return new PageRequest(pageNumber-1, pageSize);
    }

    public PageRequest toPageRequest(Sort sort){
        //从第一页开始
        if(pageNumber<1) pageNumber = 1;
        if(pageSize < 1) pageSize = 20;
//       防止请求页数过大，整垮服务器
        if(pageSize>100) pageSize = 100;
        return new PageRequest(pageNumber-1, pageSize,sort);
    }

    public int getStartIndex(){
        return (this.pageNumber-1)*this.pageSize;
    }
}
