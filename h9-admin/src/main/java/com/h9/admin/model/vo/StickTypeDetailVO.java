package com.h9.admin.model.vo;

import com.h9.common.db.entity.community.StickType;

import org.springframework.beans.BeanUtils;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/23
 */
@Data
public class StickTypeDetailVO {
    private Long id;

    private String name;

    private String content;

    private String image;

    private Integer stickCount;

    private Integer limitState = 1;

    private Integer examineState = 1;

    private Integer commentState = 1;

    private Integer admitsState = 1;

    private String sort;

    private Integer defaultSort = 1;

    private Integer state = 1;



    public StickTypeDetailVO(StickType stickType) {
        BeanUtils.copyProperties(stickType,this);
    }
}
