package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.entity.user.User;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/22
 */
@Data
public class PersonalGiveRewardVO {
    private String words;
    private BigDecimal reward = new BigDecimal(0);
    private String stickContent;
    private Long stickId;
    private String stickTitle;
    private String stickUserName;
    private String icon;

    public PersonalGiveRewardVO(StickReward stickReward){
        this.words = stickReward.getWords();
        Stick stick = stickReward.getStick();
        if (stick != null){
            this.stickContent = stick.getContent();
            this.stickTitle = stick.getTitle();
            this.stickId = stick.getId();
            User user = stick .getUser();
            if (user != null){
                this.stickUserName = user.getNickName();
            }
        }
        this.reward = stickReward.getReward();
    }

}
