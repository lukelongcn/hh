package com.h9.api.model.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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
    @NotBlank(message = "请填写nickName")
    private String nickName;
    private String sex;
    private Date birthday;
    private String marriageStatus;
    private String education;
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


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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



    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
