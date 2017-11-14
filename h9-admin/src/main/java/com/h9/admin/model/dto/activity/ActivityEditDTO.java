package com.h9.admin.model.dto.activity;

import com.h9.common.db.entity.Activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author: George
 * @date: 2017/11/4 17:24
 */
@ApiModel("活动-增加-参数")
public class ActivityEditDTO extends  ActivityAddDTO{

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空")
    //@Max(value = 20,message = "id不能大于20")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity toActivity(){
        Activity activity = new Activity();
        BeanUtils.copyProperties(this,activity);
        return  activity;
    }


}
