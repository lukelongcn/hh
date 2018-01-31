package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.entity.user.User;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/19
 */
@Data
public class StickRewardUser {
    private Long userId;
    private String avatar ;
    private String nickName;

    public StickRewardUser(User user){
        this.userId = user.getId();
        this.avatar = user.getAvatar();
        this.nickName = user.getNickName();
    }
}
