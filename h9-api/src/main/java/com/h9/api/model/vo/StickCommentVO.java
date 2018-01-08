package com.h9.api.model.vo;

import com.h9.common.db.entity.user.User;

/**
 * Created by 李圆 on 2018/1/8
 */
public class StickCommentVO {
    private Long id;
    private Long commentUserId;
    private String avatar ;
    private String nickName;
    private Integer sex = 1;

    // 评论间隔时间
    private String spaceTime;

    // 评论级别 0:帖子回复 1:评论回复
    private Integer level = 1;

    // 楼层信息
    private Integer floor = 1;

    // @的用户昵称
    private String aitNickName;

    // @的用户id
    private Long aitUserId;


}

