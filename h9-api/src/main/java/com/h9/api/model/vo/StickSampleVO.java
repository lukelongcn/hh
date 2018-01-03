package com.h9.api.model.vo;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.User;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickSampleVO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 16:21
 */
@Data
public class StickSampleVO {
    private long id;
    private String userName;
    private String userAvatar;
    private String title;
    private List<String> images;
    private Integer readCount = 0;
    private Integer likeCount = 0;
    private Integer answerCount = 0;
    private String typeName;

    public StickSampleVO() {
    }

    public StickSampleVO(Stick stick) {
        id = stick.getId();
        User user = stick.getUser();
        if(user!=null){
            userName = user.getNickName();
            userAvatar = user.getAvatar();
        }
        title = stick.getTitle();
        readCount = stick.getReadCount();
        likeCount = stick.getLikeCount();
        answerCount = stick.getAnswerCount();
        StickType stickType = stick.getStickType();
        if (stickType != null) {
            typeName = stickType.getName();
        }
    }


}
