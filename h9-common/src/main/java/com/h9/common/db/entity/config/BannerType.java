package com.h9.common.db.entity.config;

import com.h9.common.base.BaseEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:banner类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 18:13
 */
@Entity
@Table(name = "banner_type")
public class BannerType extends BaseEntity {



    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型标识'")
    private String code;

    @Column(name = "location",columnDefinition = "tinyint default 1 COMMENT '显示位置，1:首页，2:酒元商城,3社区首页 ，4帖子详情' ")
    private Integer location;

    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用'")
    private Integer enable;

    @Column(name = "start_time",nullable = false,columnDefinition = "datetime COMMENT '开始时间'")
    @Temporal(TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time",nullable = false,columnDefinition = "datetime COMMENT '结束时间'")
    @Temporal(TIMESTAMP)
    private Date endTime;

    @Transient
    private String locationDesc;

    @Transient
    private Integer bannerCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBannerCount() {
        return bannerCount;
    }

    public void setBannerCount(Integer bannerCount) {
        this.bannerCount = bannerCount;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public BannerType() {
    }

    public BannerType(BannerType bannerType,int bannerCount) {
        BeanUtils.copyProperties(bannerType,this);
        this.bannerCount = bannerCount;
    }

    public BannerType(String name, String code, Integer enable) {
        this.name = name;
        this.code = code;
        this.enable = enable;
    }

    public BannerType(String name, String code, Integer enable, Date startTime, Date endTime) {
        this.name = name;
        this.code = code;
        this.enable = enable;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public enum EnableEnum {
        DISABLED(0,"禁用"),
        ENABLED(1,"启用");

        EnableEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
