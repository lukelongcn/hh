package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import io.swagger.models.auth.In;
import org.hibernate.dialect.unique.DB2UniqueDelegate;

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
 * Time: 10:20
 */

@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "activity_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '活动名称'")
    private String activityName;

    /****
     * TODO 可能要考虑将时间做成多配置的
     */
    @Column(name = "limit_date_type",nullable = false,columnDefinition = "tinyint default 0 COMMENT '奖励次数限制0 不限制 1 秒 2 分 3小时 4天 5周 6月 7 年'")
    private Integer limitDateType = 0;
    @Column(name = "limit_date",nullable = false,columnDefinition = "int default 0 COMMENT '限制的时间天数'")
    private Integer limitDate = 0;
    @Column(name = "limit_count",nullable = false,columnDefinition = "int default 0 COMMENT '限制次数'")
    private Integer limitCount = 0 ;

    @Column(name = "share_count",nullable = false,columnDefinition = "int default 0 COMMENT '奖励可以分享使用次数'")
    private Integer shareCount = 0;


    @Column(name = "code", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '奖励口令'")
    private String code;
//  //移到批次表
//    @Column(name = "reward_count",nullable = false,columnDefinition = "int default 0 COMMENT '奖励总数量'")
//    private Integer rewardCount;

    @Column(name = "activity_url", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '活动路径'")
    private String activityUrl;

    @Column(name = "activity_icon", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '图片路径'")
    private String activityIcon;

    @Column(name = "activity_desc", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '活动描述'")
    private String activityDesc;

    @Temporal(TIMESTAMP)
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '活动开始时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '活动结束时间'")
    private Date endTime;

    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '0 禁用  1 启用'")
    private Integer enable = 0;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getLimitDateType() {
        return limitDateType;
    }

    public void setLimitDateType(Integer limitDateType) {
        this.limitDateType = limitDateType;
    }

    public Integer getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Integer limitDate) {
        this.limitDate = limitDate;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(String activityIcon) {
        this.activityIcon = activityIcon;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
