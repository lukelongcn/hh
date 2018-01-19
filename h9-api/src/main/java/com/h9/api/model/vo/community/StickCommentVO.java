package com.h9.api.model.vo.community;

import com.h9.api.model.vo.community.StickCommentSimpleVO;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/8
 */
@Data
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

    // 回复的回复列表
    private List<StickCommentSimpleVO> list;

    // 回复内容
    private String content;

    public StickCommentVO(Integer sex, StickComment stickComment,List<StickCommentSimpleVO> list) {
        this.id = stickComment.getId();
        User user = stickComment.getAnswerUser();
        if (user != null){
            this.commentUserId = user.getId();
            this.avatar = user.getAvatar();
            this.nickName = user.getNickName();
        }
        this.sex = sex;
        this.level = stickComment.getLevel();
        User userAit = stickComment.getNotifyUserId();
        if (userAit != null){
            this.aitNickName = userAit.getNickName();
            this.aitUserId = userAit.getId();
        }

        this.spaceTime = DateUtil.getSpaceTime(stickComment.getCreateTime(),new Date());
        this.content = stickComment.getContent();
        this.list = list;
    }

    public StickCommentVO() {

    }
}

