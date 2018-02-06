package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import java.util.Date;

import lombok.Data;


/**
 * Created by 李圆 on 2018/1/22
 */
@Data
public class PersonalStickVO {
    private Long stickId;
    private Long userId;
    private String avatar ;
    private String nickName;
    private String title = "";
    private String content;
    private String typeName;
    private Integer readCount = 0;
    private Integer likeCount = 0;
    private Integer answerCount = 0;
    private Integer rewardCount = 0;
    private String createTime;

    public PersonalStickVO(Stick stick){
        User user = stick.getUser();
        if (user != null){
            this.userId = user.getId();
            this.avatar = user.getAvatar();
            this.nickName = user.getNickName();
        }
        this.title = stick.getTitle();
        this.content = stick.getContent();
        StickType stickType = stick.getStickType();
        if (stickType != null){
            this.typeName = stickType.getName();
        }
        this.readCount = stick.getReadCount();
        this.answerCount = stick.getAnswerCount();
        this.likeCount = stick.getLikeCount();
        this.rewardCount = stick.getRewardCount();
        this.createTime = DateUtil.getSpaceTime(stick.getCreateTime(),new Date());
        this.stickId = stick.getId();
    }

}
