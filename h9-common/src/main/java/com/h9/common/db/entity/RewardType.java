package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import io.swagger.models.auth.In;
import org.hibernate.dialect.unique.DB2UniqueDelegate;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 10:20
 */

@Entity
@Table(name = "reward_type")
public class RewardType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "activity_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '活动名称'")
    private String activityName;

    /****
     * TODO 可能要考虑将时间做成多配置的
     */
    @Column(name = "limit_date_type",nullable = false,columnDefinition = "tinyint default 1 COMMENT '奖励次数限制0 不限制 1 秒 2 分 3小时 4天 5周 6月 7 年'")
    private Integer limitDateType = 0;

    @Column(name = "limit_date",nullable = false,columnDefinition = "int default 0 COMMENT '限制的时间天数'")
    private Integer limitDate = 0;

    @Column(name = "limit_count",nullable = false,columnDefinition = "int default 0 COMMENT '限制次数'")
    private Integer limitCount = 0 ;

    @Column(name = "share_status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否可以被分享'")
    private Integer shareStatus = 1;

    @Column(name = "share_count",nullable = false,columnDefinition = "int default 0 COMMENT '奖励可以分享使用次数'")
    private Integer shareCount = 0;

    @Column(name = "user_count",nullable = false,columnDefinition = "int default 0 COMMENT '单个用户单个条码次数'")
    private Integer userCount;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '奖励代码'")
    private String code;

    @Column(name = "reward_count",nullable = false,columnDefinition = "int default 0 COMMENT '奖励总数量'")
    private Integer rewardCount;

    @Column(name = "reward_money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '单个奖励金额'")
    private BigDecimal rewardMoney = new BigDecimal(0);


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

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(Integer rewardCount) {
        this.rewardCount = rewardCount;
    }

    public BigDecimal getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(BigDecimal rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
}
