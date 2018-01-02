package com.h9.api.model.vo;

import com.h9.common.db.entity.community.StickType;
import org.springframework.beans.BeanUtils;


/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickTypeVO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 15:05
 */

public class StickTypeVO {

    public StickTypeVO(StickType stickType) {
        BeanUtils.copyProperties(stickType,this);
    }

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
