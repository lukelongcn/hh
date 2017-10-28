package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 10:21
 */

@Entity
@Table(name = "reward_flow")
public class RewardFlow extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '奖励领取用户id'")
    private Long userId;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '奖励条码'")
    private String code;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "reward_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '奖励id'")
    private Reward reward;

    @Column(name = "first_user",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 第一个用户 '")
    private Integer firstUser = 1;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励领取金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "longitude", columnDefinition = "double default null COMMENT ''")
    private double longitude;

    @Column(name = "latitude", columnDefinition = "double default null COMMENT ''")
    private double latitude;

    @Column(name = "address", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '地址'")
    private String address;

    @Column(name = "refer", nullable = false, columnDefinition = "varchar(500) default '' COMMENT '前置页面'")
    private String refer;

    @Column(name = "user_agent", nullable = false, columnDefinition = "varchar(500) default '' COMMENT '用户代理'")
    private String userAgent;

    @Column(name = "ip", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'ip地址'")
    private String ip;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 未分配奖励 2已分配奖励 3完成'")
    private Integer status = 1;

    @Temporal(TIMESTAMP)
    @Column(name = "finish_time", columnDefinition = "datetime COMMENT '结束时间'")
    private Date finishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public Integer getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(Integer firstUser) {
        this.firstUser = firstUser;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
