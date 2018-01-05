package com.h9.api.model.vo;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/5
 */
@Data
public class StickSearchVO {
    private long id;
    private String createTime;
    private String title;
    private String userName;

    public StickSearchVO(Stick stick){
        BeanUtils.copyProperties(stick,this);
        createTime = DateUtil.formatDate(stick.getCreateTime(), DateUtil.FormatType.DAY);
    }
}
