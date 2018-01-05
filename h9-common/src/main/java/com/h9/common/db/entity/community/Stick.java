package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.ArrayList;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 14:03
 */

@Entity
@Table(name = "stick")
public class Stick extends BaseEntity {
    
    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "stick_type_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '所属分类'")
    private StickType stickType;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  default '' COMMENT '标题'")
    private String title = "";

    @Column(name = "content",  columnDefinition = "text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default null  COMMENT '内容'" )
    private String content = "";

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '发帖用户'")
    private User user;

    @Column(name = "read_count",nullable = false,columnDefinition = "int default 0 COMMENT '阅读数量'")
    private Integer readCount = 0;

    @Column(name = "like_count",nullable = false,columnDefinition = "int default 0 COMMENT '帖子点赞数量'")
    private Integer likeCount = 0;

    @Column(name = "answer_count",nullable = false,columnDefinition = "int default 0 COMMENT '回答数量'")
    private Integer answerCount = 0;

    /***************************      地址信息              *************************/
    @Column(name = "longitude", columnDefinition = "double default 0 COMMENT '经度'")
    private double longitude;

    @Column(name = "latitude", columnDefinition = "double default 0 COMMENT '维度'")
    private double latitude;

    @Column(name = "address", columnDefinition = "varchar(64) default '' COMMENT '地址'")
    private String address;

    @Column(name="province",columnDefinition = "varchar(50) COMMENT '省'")
    private String province;

    @Column(name="city",columnDefinition = "varchar(50) COMMENT '市'")
    private String city;

    @Column(name="district",columnDefinition = "varchar(50) COMMENT '区'")
    private String district;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StickType getStickType() {
        return stickType;
    }

    public void setStickType(StickType stickType) {
        this.stickType = stickType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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
