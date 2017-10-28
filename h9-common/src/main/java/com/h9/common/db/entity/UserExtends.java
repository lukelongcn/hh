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
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户'")
    private Long userId;

    @Column(name = "sex",nullable = false,columnDefinition = "tinyint default 0 COMMENT ' 1 男 2 女 0未知'")
    private Integer sex = 1;

    @Temporal(DATE)
    @Column(name = "birthday", columnDefinition = "DATE COMMENT '生日'")
    private Date birthday;

    @Column(name = "emotion",nullable = false,columnDefinition = "int default 0 COMMENT '情感'")
    private Integer emotion;

    @Column(name = "education",nullable = false,columnDefinition = "int default 0 COMMENT '教育'")
    private Integer education;

    @Column(name = "job", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '职业'")
    private String job;

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

    public Integer getEmotion() {
        return emotion;
    }

    public void setEmotion(Integer emotion) {
        this.emotion = emotion;
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
