package com.h9.admin.model.vo;

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
    private Long stickId;
    private Long stickUserId;
    private String nickName;
    private Integer state;
    private Integer operationState;
    private String ip;


    // 评论间隔时间
    private String spaceTime;

    // 回复的回复列表
    private List<StickCommentSimpleVO> list;

    // 回复内容
    private String content;

    public StickCommentVO(StickComment stickComment,List<StickCommentSimpleVO> list) {
        this.id = stickComment.getId();
        this.stickId = stickComment.getStick().getId();
        User user = stickComment.getStick().getUser();
        if (user != null){
            this.stickUserId = user.getId();
            this.nickName = user.getNickName();
        }
        this.ip = stickComment.getIp();
        this.state = stickComment.getOperationState();
        this.operationState = stickComment.getOperationState();
        this.spaceTime = DateUtil.getSpaceTime(stickComment.getCreateTime(),new Date());
        this.content = stickComment.getContent();
        this.list = list;
    }

    public StickCommentVO() {

    }
}

