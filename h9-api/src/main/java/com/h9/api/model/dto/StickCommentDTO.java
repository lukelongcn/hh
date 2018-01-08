package com.h9.api.model.dto;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.user.User;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/8
 */
@Data
public class StickCommentDTO {
    private Long id;

    private Long answerUser;

    @NotNull(message = "贴子id不能为空")
    private Long stickId;

    @NotBlank(message = "请输入回复内容")
    private String content;

    private Long stickCommentId;

    @NotNull(message = "回复级别不能为空，0:帖子回复 1:评论回复")
    private Integer level = 1;

    private Integer floor = 1;

    private Long notifyUserId;

    private Integer likeCount = 0;
}
