package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import org.springframework.stereotype.Component;


import lombok.Data;

/**
 * Created by 李圆 on 2018/1/22
 */
@Data
public class PersonalCommentVO {
    private Long commentId;
    private Long stickId;
    private String content;
    private String createTime;
    private Integer floor;
    private String title = "";
    private Long stickUserId;
    private String stickNickName;
    private Long pCommentId;
    private String pContent;

    public PersonalCommentVO(StickComment stickComment){
        this.commentId = stickComment.getId();
        this.content = stickComment.getContent();
        this.createTime = DateUtil.formatDate(stickComment.getCreateTime(), DateUtil.FormatType.SECOND);
        this.floor = stickComment.getFloor();
        Stick stick = stickComment.getStick();
        if (stick != null){
            this.title = stick.getTitle();
            User user = stick.getUser();
            if (user != null){
                this.stickUserId = user.getId();
                this.stickNickName = user.getNickName();
            }
        }
        StickComment pStickComment = stickComment.getStickComment();
        if (pStickComment != null){
            this.pCommentId = pStickComment.getId();
            this.pContent = pStickComment.getContent();
        }
    }

}
