package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

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

    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用'")
    private Integer enable;

    @Column(name = "start_time",columnDefinition = "datetime COMMENT '开始时间'")
    @Temporal(TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time",columnDefinition = "datetime COMMENT '结束时间'")
    @Temporal(TIMESTAMP)
    private Date endTime;

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
