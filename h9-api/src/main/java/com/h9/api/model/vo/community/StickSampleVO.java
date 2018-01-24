package com.h9.api.model.vo.community;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.User;
import com.h9.common.modle.vo.Config;
import com.h9.common.utils.DateUtil;

import org.w3c.dom.ls.LSInput;

import lombok.Data;

import javax.persistence.Column;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private String spaceTime;

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
        if(stick.getReadCount()!=null) readCount = stick.getReadCount();
        if(stick.getLikeCount()!=null)likeCount = stick.getLikeCount();
        if(stick.getAnswerCount()!=null) answerCount = stick.getAnswerCount();
        StickType stickType = stick.getStickType();
        if (stickType != null) {
            typeName = stickType.getName();
        }
        spaceTime = DateUtil.getSpaceTime(stick.getCreateTime(),new Date());
        images = stick.getImages();
        System.out.println(stick.getImages());
    }

}
