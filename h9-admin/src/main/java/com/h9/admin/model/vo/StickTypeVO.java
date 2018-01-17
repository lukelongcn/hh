package com.h9.admin.model.vo;

import com.h9.common.db.entity.community.StickType;

import org.springframework.beans.BeanUtils;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/17
 */
@Data
public class StickTypeVO {
    private Long id;
    private Integer defaultSort = 1;
    private String image;
    private String name;

    public StickTypeVO(StickType stickType) {
        BeanUtils.copyProperties(stickType,this);
    }
}
