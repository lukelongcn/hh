package com.h9.admin.model.dto.community;

import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/3 14:46
 */
@ApiModel("功能-增加-参数")
public class BannerAddDTO {

    @ApiModelProperty(value = "功能类型id",required = true)
    @NotNull(message = "功能类型不能为空")
    private Long bannerTypeId;

    @ApiModelProperty(value = "名称",required = true)
    @NotBlank(message = "名称不能为空")
    @Size(max=128,message = "名称过长")
    private String title;

    @ApiModelProperty(value = "动作",required = true)
    //@NotBlank(message = "动作")
    private String url;

    @ApiModelProperty(value = "图标url",required = true)
    @NotBlank(message = "图标url名称不能为空")
    private String icon;

    @ApiModelProperty(value = "状态，1：启用 0：禁用",required = true)
    @NotNull(message = "状态不能为空")
    private Integer enable;

    @ApiModelProperty(value = "上线开始时间")
    @NotNull(message = "上线开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "上线结束时间")
    @NotNull(message = "上线结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "排序",required = true)
    //@NotNull(message = "排序不能为空")
    @Min(value = 0,message = "排序号不能小于0")
    @Max(value = 127,message = "排序号不能大于127")
    private Integer sort ;

    @ApiModelProperty(value = "字体颜色")
    private String fontColor;

    public Long getBannerTypeId() {
        return bannerTypeId;
    }

    public void setBannerTypeId(Long bannerTypeId) {
        this.bannerTypeId = bannerTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort==null?1:sort;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public Banner toBanner(){
        Banner banner = new Banner();
        BeanUtils.copyProperties(this,banner);
        return  banner;
    }
}
