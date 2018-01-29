package com.h9.api.model.vo.community;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.vo.HomeVO;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.user.User;
import com.h9.common.modle.vo.Config;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/5
 */
@Data
public class StickDetailVO {
    private long userId;
    private long id;
    private String userName;
    private String userAvatar;
    private String title;
    private List<String> images;
    private Integer readCount = 0;
    private Integer likeCount = 0;
    private Integer answerCount = 0;
    private Integer rewardCount = 0;
    private String typeName;
    private String content;
    private String createTime;
    private Map<String, List<HomeVO>> listMap;
    private Integer state;
    private List<StickRewardUser> stickRewardUserList;
    // 经度
    private double longitude;

    //维度
    private double latitude;

    private Long stickTypeId;

    // 地址
    private String address;


    public StickDetailVO(Stick stick) {
        id = stick.getId();
        User user = stick.getUser();
        if(user!=null){
            userId = user.getId();
            userName = user.getNickName();
            userAvatar = user.getAvatar();
        }
        BeanUtils.copyProperties(stick,this);
        createTime = DateUtil.formatDate(stick.getCreateTime(), DateUtil.FormatType.DAY);
        StickType stickType = stick.getStickType();
        if (stickType != null) {
            stickTypeId = stickType.getId();
            typeName = stickType.getName();
        }

    }


}
