package com.h9.admin.model.dto;

import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author: George
 * @date: 2017/11/3 14:39
 */
@ApiModel("功能-编辑-参数")
public class BannerEditDTO extends BannerAddDTO{

    @ApiModelProperty(value = "id",required = true)
    @NotEmpty(message = "id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Banner toBanner(){
        Banner banner = super.toBanner();
        banner.setId(this.id);
        return  banner;
    }

}
