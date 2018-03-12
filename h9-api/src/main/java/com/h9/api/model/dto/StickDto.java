package com.h9.api.model.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * StickDto:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 15:23
 */
public class StickDto {
    @NotBlank(message = "请填写标题")
    @Length(min = 0,max = 64,message = "标题填写过长")
    private String title;
    @NotBlank(message = "请填写内容")
    @Length(min = 0,max = 1000,message = "内容过长")
    private String content;
    @NotNull(message = "请选择分类")
    private Long typeId;
    private double longitude;
    private double latitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
