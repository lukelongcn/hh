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
    private Long stickTypeId;
    private String stickTypeName;
    private String title = "";
    private String content = "";
    private Long userId;
    private String nickName;
    private Integer lockState = 1;
    private Integer operationState = 1;
    private String ip;
    private Integer state = 1;
    private Integer rewardCount = 0;
    private BigDecimal rewardMoney = new BigDecimal(0);
    private String createTime;
    private String url;

    public StickDetailVO(Stick stick){
        id = stick.getId();
        stickTypeId = stick.getStickType().getId();
        stickTypeName = stick.getStickType().getName();
        title = stick.getTitle();
        content = stick.getContent();
        userId = stick.getUser().getId();
        nickName = stick.getUser().getNickName();
        lockState = stick.getLockState();
        rewardCount = stick.getRewardCount();
        createTime = DateUtil.getSpaceTime(stick.getCreateTime(),new Date());
    }
}
