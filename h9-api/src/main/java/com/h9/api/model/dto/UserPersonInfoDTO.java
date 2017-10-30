package com.h9.api.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by itservice on 2017/10/28.
 */
public class UserPersonInfoDTO {
    private String avatar;
    @Size(min = 1,message = "请填写nickName")
    @NotNull(message = "请填写nickName")
    private String nickName;
    private Integer sex;
    private Date birthday;
    private Integer marriageStatus;
    private Integer education;
    private String job;



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



    public Integer getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date bir = format.parse("1995-09-04");
            this.birthday = bir;
        } catch (ParseException e) {
        }
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
