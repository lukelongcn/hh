package com.h9.admin.model.dto;

import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/2 14:57
 */
@ApiModel("功能分类-编辑-参数")
public class BannerTypeEditDTO extends BannerTypeAddDTO {

    @NotNull(message = "id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BannerType toBannerType(){
        BannerType bannerType = super.toBannerType();
        bannerType.setId(this.id);
        return  bannerType;
    }

}
