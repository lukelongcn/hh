package com.h9.admin.model.vo;

import com.h9.common.db.entity.community.StickReport;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/12
 */
@Data
public class StickReportVO {
    private Long id;

    private Long userId;

    private Long stickId;

    private String title;

    private String content;

    private String createTime;

    public StickReportVO(StickReport stickReport) {
        BeanUtils.copyProperties(stickReport,this);
        this.createTime = DateUtil.formatDate(stickReport.getCreateTime(), DateUtil.FormatType.SECOND);
        this.title = stickReport.getStick().getTitle();
        this.stickId = stickReport.getStick().getId();
    }
}
