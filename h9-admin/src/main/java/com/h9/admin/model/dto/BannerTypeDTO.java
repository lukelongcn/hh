package com.h9.admin.model.dto;

import com.h9.common.db.entity.BannerType;
import com.h9.common.utils.DateUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author: George
 * @date: 2017/11/2 10:42
 */
public class BannerTypeDTO {

    private Long id;

    @NotEmpty(message = "名称不能为空")
    private String name;

    private String code;

    private Integer enable;

   // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

   // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

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
