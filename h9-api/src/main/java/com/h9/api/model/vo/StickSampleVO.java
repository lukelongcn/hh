package com.h9.api.model.vo;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.user.User;

import javax.persistence.Column;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickSampleVO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 16:21
 */
public class StickSampleVO {
    private long id;
    private String userName;
    private String userAvatar;
    private String title;
    private List<String> images;
    private Integer readCount = 0;
    private Integer likeCount = 0;
    private Integer answerCount = 0;

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
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }
}
