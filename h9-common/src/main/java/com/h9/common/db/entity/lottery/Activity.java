package com.h9.common.db.entity.lottery;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:抽奖活动配置
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
     *  可能要考虑将时间做成多配置的
     */
    @Column(name = "limit_date_type",nullable = false,columnDefinition = "tinyint default 0 COMMENT '奖励次数限制0 不限制 1 秒 2 分 3小时 4天 5周 6月 7 年'")
    private Integer limitDateType = 0;
    @Column(name = "limit_date",nullable = false,columnDefinition = "int default 0 COMMENT '限制的时间天数'")
    private Integer limitDate = 0;
    @Column(name = "limit_count",nullable = false,columnDefinition = "int default 0 COMMENT '限制次数'")
    private Integer limitCount = 0 ;

    @Column(name = "share_count",nullable = false,columnDefinition = "int default 0 COMMENT '奖励可以分享使用次数'")
    private Integer shareCount = 0;

    @Column(name = "code", columnDefinition = "varchar(32) default '' COMMENT '奖励口令'")
    private String code;

    @Column(name = "is_push",nullable = false,columnDefinition = "tinyint default 0 COMMENT '回复关键字是否推送活动 0：不推送  1：推送'")
    private Integer isPush = 0;

    //  //移到批次表
//    @Column(name = "reward_count",nullable = false,columnDefinition = "int default 0 COMMENT '奖励总数量'")
//    private Integer rewardCount;

    @Column(name = "activity_url",  columnDefinition = "varchar(256) default '' COMMENT '活动路径'")
    private String activityUrl;

    @Column(name = "activity_icon",  columnDefinition = "varchar(256) default '' COMMENT '图片路径'")
    private String activityIcon;

    @Column(name = "activity_desc",  columnDefinition = "varchar(128) default '' COMMENT '活动描述'")
    private String activityDesc;

    @Temporal(TIMESTAMP)
    @Column(name = "start_time",nullable = false, columnDefinition = "datetime COMMENT '活动开始时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time",nullable = false, columnDefinition = "datetime COMMENT '活动结束时间'")
    private Date endTime;

    @Column(name = "need_phone",nullable = false,columnDefinition = "tinyint default 0 COMMENT '是否需要手机号 0：需要  1：不需要'")
    private Integer needPhone = 0;

    @Column(name = "need_sms",nullable = false,columnDefinition = "tinyint default 0 COMMENT '是否需要短信验证 0：需要  1：不需要'")
    private Integer needSms = 0;


    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '0 禁用  1 启用'")
    private Integer enable = 0;

    @Column(name = "person_total_number",nullable = false,columnDefinition = "tinyint default 0 COMMENT '每人总次数'")
    private Integer personTotalNumber = 0;

    @Column(name = "person_daily_umber",nullable = false,columnDefinition = "tinyint default 0 COMMENT '每人每天次数'")
    private Integer personDailyNumber = 0;

    @Column(name = "person_daily_target_number",nullable = false,columnDefinition = "tinyint default 0 COMMENT '每人每天中奖次数'")
    private Integer personDailyTargetNumber = 0;

    @Column(name = "target_period",nullable = false,columnDefinition = "int default 0 COMMENT '时间间隔'")
    private Integer targetPeriod = 0;

    @Column(name = "participation_frequency",nullable = false,columnDefinition = "int default 0 COMMENT '参与频率'")
    private Integer participationFrequency = 0;

   /* @Column(name = "share_title", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '分享标题'")
    private String shareTitle;

    @Column(name = "share_img", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '分享图片'")
    private String shareImg;

    @Column(name = "share_desc", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '分享描述'")
    private String shareDesc;

    @Column(name = "img_text_title", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '图文标题'")
    private String imgTextTitle;

    @Column(name = "img_text_url", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '图文图片'")
    private String imgTextUrl;

    @Column(name = "img_text_desc", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '图文描述'")
    private String imgTextDesc;*/

    /*@Column(name = "target_count",columnDefinition = "int default 0 COMMENT '中奖人数'")
    private Integer targetCount = 0;

    @Column(name = "target_rate",  columnDefinition = "varchar(256) default '' COMMENT '中奖比例'")
    private String targetRate;*/

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

    public Integer getIsPush() {
        return isPush;
    }

    public void setIsPush(Integer isPush) {
        this.isPush = isPush;
    }

    public Integer getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Integer needPhone) {
        this.needPhone = needPhone;
    }

    public Integer getNeedSms() {
        return needSms;
    }

    public void setNeedSms(Integer needSms) {
        this.needSms = needSms;
    }

    public Integer getPersonTotalNumber() {
        return personTotalNumber;
    }

    public void setPersonTotalNumber(Integer personTotalNumber) {
        this.personTotalNumber = personTotalNumber;
    }

    public Integer getPersonDailyNumber() {
        return personDailyNumber;
    }

    public void setPersonDailyNumber(Integer personDailyNumber) {
        this.personDailyNumber = personDailyNumber;
    }

    public Integer getPersonDailyTargetNumber() {
        return personDailyTargetNumber;
    }

    public void setPersonDailyTargetNumber(Integer personDailyTargetNumber) {
        this.personDailyTargetNumber = personDailyTargetNumber;
    }

    public Integer getTargetPeriod() {
        return targetPeriod;
    }

    public void setTargetPeriod(Integer targetPeriod) {
        this.targetPeriod = targetPeriod;
    }

    public Integer getParticipationFrequency() {
        return participationFrequency;
    }

    public void setParticipationFrequency(Integer participationFrequency) {
        this.participationFrequency = participationFrequency;
    }

   /* public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getImgTextTitle() {
        return imgTextTitle;
    }

    public void setImgTextTitle(String imgTextTitle) {
        this.imgTextTitle = imgTextTitle;
    }

    public String getImgTextUrl() {
        return imgTextUrl;
    }

    public void setImgTextUrl(String imgTextUrl) {
        this.imgTextUrl = imgTextUrl;
    }

    public String getImgTextDesc() {
        return imgTextDesc;
    }

    public void setImgTextDesc(String imgTextDesc) {
        this.imgTextDesc = imgTextDesc;
    }*/

    /*public Integer getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(Integer targetCount) {
        this.targetCount = targetCount;
    }

    public List<TargetRateDTO> getTargetRate() {
        if(StringUtils.isEmpty(targetRate)){
            return null;
        }
        return JSON.parseArray(targetRate,TargetRateDTO.class);
    }

    public void setTargetRate(List<TargetRateDTO> targetRate) {
        this.targetRate = JSON.toJSONString(targetRate);
    }*/

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
