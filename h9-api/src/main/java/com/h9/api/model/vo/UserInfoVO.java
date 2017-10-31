package com.h9.api.model.vo;

import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserExtends;
import com.h9.common.utils.DateUtil;

/**
 * Created by itservice on 2017/10/31.
 */
public class UserInfoVO {
    private String avatar;
    private String nickName;
    private String tel;
    private Integer sex;
    private String birthday;
    private Integer marriageStatus;
    private Integer education;
    private String job;


    public UserInfoVO(){}
    public static UserInfoVO convert(User user, UserExtends userExtends){
        UserInfoVO vo = new UserInfoVO();
        vo.setAvatar(user.getAvatar());
        vo.setNickName(user.getNickName());
        vo.setTel(user.getPhone());
        vo.setSex(userExtends.getSex());
        vo.setBirthday(DateUtil.formatDate(userExtends.getBirthday(), DateUtil.FormatType.GBK_DAY));
        vo.setMarriageStatus(userExtends.getMarriageStatus());
        vo.setEducation(userExtends.getEducation());
        vo.setJob(userExtends.getJob());
        return vo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
