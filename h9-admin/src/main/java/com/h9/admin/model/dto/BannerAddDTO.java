package com.h9.admin.model.dto;

import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/3 14:46
 */
@ApiModel("功能-增加-参数")
public class BannerAddDTO {

    @ApiModelProperty(value = "功能类型，只需传id",required = true)
    @NotEmpty(message = "功能类型不能为空")
    private BannerType bannerType;

    @ApiModelProperty(value = "名称",required = true)
    @NotEmpty(message = "名称不能为空")
    private String title;

   /* @ApiModelProperty(value = "名称",required = true)
    @NotEmpty(message = "名称不能为空")
    private String content;*/

    @ApiModelProperty(value = "动作",required = true)
    @NotEmpty(message = "动作")
    private String url;

    @ApiModelProperty(value = "图标url",required = true)
    @NotEmpty(message = "图标url名称不能为空")
    private String icon;

    @ApiModelProperty(value = "状态，1：启用 0：禁用",required = true)
    @NotEmpty(message = "状态不能为空")
    private Integer enable;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "排序",required = true)
    @NotEmpty(message = "排序不能为空")
    private Integer sort = 1;

    @ApiModelProperty(value = "字体颜色")
    private String fontColor;


    public BannerType getBannerType() {
        return bannerType;
    }

    public void setBannerType(BannerType bannerType) {
        this.bannerType = bannerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/

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
        this.sort = sort;
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
