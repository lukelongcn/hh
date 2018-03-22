package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.config.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author: George
 * @date: 2017/11/2 10:42
 */
@ApiModel("功能分类-增加-参数")
public class BannerTypeAddDTO {

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "标识", required = true)
    @NotBlank(message = "标识不能为空")
    private String code;

    @ApiModelProperty(value = "功能位置", required = true)
    @NotNull(message = "功能位置不能为空")
    private Integer location;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer enable;

    // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上线开始时间")
    @NotNull(message = "上线开始时间不能为空")
    private Date startTime;

    // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上线结束时间")
    @NotNull(message = "上线结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "排序,默认为1")
    private int sort = 1;


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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BannerType toBannerType() {
        BannerType bannerType = new BannerType();
        BeanUtils.copyProperties(this, bannerType);
        return bannerType;
    }

    public BannerType toBannerType(BannerType bannerType) {
        BeanUtils.copyProperties(this, bannerType);
        return bannerType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
