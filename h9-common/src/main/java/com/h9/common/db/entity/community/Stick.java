package com.h9.common.db.entity.community;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

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

    @Column(name = "reward_count",nullable = false,columnDefinition = "int default 0 COMMENT '打赏数量'")
    private Integer rewardCount = 0;

    @Column(name = "state",nullable = false,columnDefinition = "int default 1 COMMENT '帖子状态 1使用 2禁用 3删除'")
    private Integer state = 1;

    @Column(name = "floor_count",nullable = false,columnDefinition = "int default 1 COMMENT '楼层数量'")
    private Integer floorCount = 1;

    @Column(name = "ip",columnDefinition = "varchar(200) default '' COMMENT 'ip'")
    private String ip;

    @Column(name = "operation_state",nullable = false,columnDefinition = "int default 1 COMMENT '帖子操作状态状态 1通过 2不通过'")
    private Integer operationState = 1;

    @Column(name = "lock_state",nullable = false,columnDefinition = "int default 1 COMMENT '锁住状态 1解锁 2锁住'")
    private Integer lockState = 1;

    @Column(name = "images",columnDefinition = "varchar(200) default '' COMMENT '照片'")
    private String images;

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

    @Temporal(TIMESTAMP)
    @Column(name = "answer_time", columnDefinition = "datetime COMMENT '最后回复时间'")
    private Date answerTime;

    public Integer getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(Integer rewardCount) {
        this.rewardCount = rewardCount;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getOperationState() {
        return operationState;
    }

    public void setOperationState(Integer operationState) {
        this.operationState = operationState;
    }

    public Integer getLockState() {
        return lockState;
    }

    public void setLockState(Integer lockState) {
        this.lockState = lockState;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getImages() {
        try {
            return JSONArray.parseArray(images, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void setImages(List<String> images) {
        try {
            this.images = JSONArray.toJSONString(images);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }
}
