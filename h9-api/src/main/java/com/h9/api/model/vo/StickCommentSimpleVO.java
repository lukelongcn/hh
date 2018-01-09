package com.h9.api.model.vo;

import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.user.User;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/9
 */
@Data
public class StickCommentSimpleVO {

    // 用户id
    private Long commentUserId;

    // 昵称
    private String nickName;

    // 回复内容
    private String content;

    // 被回复人昵称
    private String backNickName;

    // 被回复人id
    private Long backCommentUserId;

    public StickCommentSimpleVO(User user,User backUser ,String content){
        this.commentUserId = user.getId();
        this.nickName = user.getNickName();
        this.content = content;
        this.backCommentUserId = backUser.getId();
        this.backNickName = backUser.getNickName();
    }
}
