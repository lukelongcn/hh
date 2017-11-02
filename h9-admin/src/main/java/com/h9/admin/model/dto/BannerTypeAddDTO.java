package com.h9.admin.model.dto;

import com.h9.common.db.entity.BannerType;
import com.h9.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author: George
 * @date: 2017/11/2 10:42
 */
@ApiModel("功能分类-增加-参数")
public class BannerTypeAddDTO {

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "标识")
    @NotEmpty(message = "标识不能为空")
    private String code;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private Integer enable;

   // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
   @ApiModelProperty(value = "开始时间")
    private Date startTime;

   // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
   @ApiModelProperty(value = "结束时间")
    private Date endTime;

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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = DateUtil.formatDate(startTime,DateUtil.FormatType.SECOND);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = DateUtil.formatDate(endTime,DateUtil.FormatType.SECOND);
    }

    public BannerType toBannerType(){
        BannerType bannerType = new BannerType();
        BeanUtils.copyProperties(this,bannerType);
        return  bannerType;
    }
}
