package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/22
 */
@Data
public class PersonalRewardedVO {
    private String rewardAvatar ;

    private String rewardNickName;

    private String words;

    private String createTime;

    private BigDecimal reward = new BigDecimal(0);

    public PersonalRewardedVO(StickReward stickReward){
        this.createTime = DateUtil.formatDate(stickReward.getCreateTime(), DateUtil.FormatType.SECOND);
        User user = stickReward.getUser();
        if (user != null){
            this.rewardAvatar = user.getAvatar();
            this.rewardNickName = user.getNickName();
        }
        this.words = stickReward.getWords();
        this.reward = stickReward.getReward();
    }
}
