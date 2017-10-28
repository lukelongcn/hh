package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:用户行为表
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 18:01
 */

@Entity
@Table(name = "userRecord")
public class UserRecord extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户'")
    private Long userId;

    @Column(name = "type",nullable = false,columnDefinition = "tinyint default 1 COMMENT '类型 ：1.红包 ：2.防伪 3.登录 4 充值，6提现'")
    private Integer type = 1;
    
    
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
    
    @Column(name = "os", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '系统'")
    private String os;
    
    @Column(name = "version", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '客户端版本'")
    private String version;
    
    @Column(name = "client",nullable = false,columnDefinition = "int default 0 COMMENT '客户端'")
    private Integer client;

    @Column(name = "imei", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '机器唯一码'")
    private String imei;

    
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
