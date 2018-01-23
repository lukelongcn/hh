package com.h9.admin.model.vo;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/17
 */
@Data
public class StickDetailVO {
    private Long id;
    private Long typeId;
    private String stickTypeName;
    private String title = "";
    private String content = "";
    private Long userId;
    private String nickName;
    private Integer lockState;
    private Integer operationState;
    private String ip;
    private Integer state;
    private Integer rewardCount;
    private BigDecimal rewardMoney = new BigDecimal(0);
    private String createTime;
    private String url;

    public StickDetailVO(Stick stick) {
        this.id = stick.getId();
        this.typeId = stick.getStickType().getId();
        this.stickTypeName = stick.getStickType().getName();
        this.title = stick.getTitle();
        this.content = stick.getContent();
        this.userId = stick.getUser().getId();
        this.nickName = stick.getUser().getNickName();
        this.lockState = stick.getLockState();
        this.operationState = stick.getOperationState();
        this.ip = stick.getIp();
        this.state = stick.getState();
        this.rewardCount = stick.getRewardCount();
        this.createTime = DateUtil.getSpaceTime(stick.getCreateTime(), new Date());
    }
    public StickDetailVO(){

    }
}
