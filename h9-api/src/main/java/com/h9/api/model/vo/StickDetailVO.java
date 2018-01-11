package com.h9.api.model.vo;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.config.Banner;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/5
 */
@Data
public class StickDetailVO {
    private long id;
    private String userName;
    private String userAvatar;
    private String title;
    private List<String> images;
    private Integer readCount = 0;
    private Integer likeCount = 0;
    private Integer answerCount = 0;
    private Integer rewardCount = 0;
    private String typeName;
    private String content;
    private String createTime;
    private Map<String, List<HomeVO>> listMap;
    private Integer state;

    public StickDetailVO(Stick stick) {
        id = stick.getId();
        User user = stick.getUser();
        if(user!=null){
            userName = user.getNickName();
            userAvatar = user.getAvatar();
        }
        title = stick.getTitle();
        readCount = stick.getReadCount();
        likeCount = stick.getLikeCount();
        rewardCount = stick.getRewardCount();
        answerCount = stick.getAnswerCount();
        createTime = DateUtil.formatDate(stick.getCreateTime(), DateUtil.FormatType.DAY);
        StickType stickType = stick.getStickType();
        if (stickType != null) {
            typeName = stickType.getName();
        }
        content = stick.getContent();
        state = stick.getState();
    }
}
