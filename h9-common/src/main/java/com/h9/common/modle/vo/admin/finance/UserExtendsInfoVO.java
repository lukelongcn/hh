package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.user.UserExtends;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/30 15:53
 */
public class UserExtendsInfoVO {

    @ApiModelProperty(value = "性别")
    private String sex ;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "情感")
    private String marriageStatus;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "职业")
    private String job;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
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

    public UserExtendsInfoVO() {
    }

    public UserExtendsInfoVO(UserExtends userExtends) {
        BeanUtils.copyProperties(userExtends,this);
    }
}
