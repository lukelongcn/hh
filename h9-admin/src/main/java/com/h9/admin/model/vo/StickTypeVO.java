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

    private String name;

    private String content;

    private String image;


    public StickTypeVO(StickType stickType) {
        BeanUtils.copyProperties(stickType,this);
    }
}
