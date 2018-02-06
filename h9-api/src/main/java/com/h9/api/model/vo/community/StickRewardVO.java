package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.user.User;
import com.h9.common.modle.vo.Config;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/10
 */
@Data
public class StickRewardVO {
    private long id;
    private String userName;
    private String userAvatar;
    private long userId;
    private List<Config> list;

    public StickRewardVO(Stick stick,List<Config> list){
        this.id = stick.getId();
        if (stick.getUser()!= null){
            User user = stick.getUser();
            this.userAvatar = user.getAvatar();
            this.userName = user.getNickName();
            this.userId = user.getId();
        }
        this.list = list;
    }

}
