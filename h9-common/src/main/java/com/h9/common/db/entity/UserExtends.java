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


    @Column(name = "marriage_status",columnDefinition = "varchar(200) default '单身' COMMENT '单身，恋受，已婚，其他(前端提供)'")
    private String marriageStatus;

    @Column(name = "education",columnDefinition = "varchar(200) default '本科' COMMENT '小学及以下 1，初中  2 ，高中    3，中专 4，本科 5" +
            "，研究生 6，博士及以上 7'")
    private String education = "5";

    @Column(name = "job",columnDefinition = "varchar(100) COMMENT '职业' ")
    private String job="";


    @Column(name = "img_id")
    private Long imgId;

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

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

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setJob(String job)

    {
        this.job = job;
    }
}
