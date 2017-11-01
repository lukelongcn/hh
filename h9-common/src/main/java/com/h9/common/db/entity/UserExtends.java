package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 18:19
 */

@Entity
@Table(name = "userExtends")
public class UserExtends extends BaseEntity {


    @Id
    @Column(name = "user_id", columnDefinition = "bigint(20)  COMMENT '用户'")
    private Long userId;

    @Column(name = "sex",nullable = false,columnDefinition = "int default 1 COMMENT ' 1 为男 0为女'")
    private Integer sex = 1;
    @Column(name = "birthday",columnDefinition = "datetime COMMENT '生日'")
    private Date birthday;


    @Column(name = "marriage_status",columnDefinition = "int default 1 COMMENT '1 单身，2恋受，3已婚，4其他'")
    private Integer marriageStatus;

    @Column(name = "education",columnDefinition = "int default 0 COMMENT '小学及以下 1，初中  2 ，高中    3，中专    4，本科    5" +
            "，研究生   6，博士及以上 7'")
    private Integer education = 0;

    @Column(name = "job",columnDefinition = "varchar(100) COMMENT '职业' ")
    private String job="";


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
