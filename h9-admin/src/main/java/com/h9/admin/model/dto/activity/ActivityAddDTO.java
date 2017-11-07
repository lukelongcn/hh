package com.h9.admin.model.dto.activity;

import com.h9.common.db.entity.Activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/4 17:24
 */
@ApiModel("活动-编辑-参数")
public class ActivityAddDTO {


    @ApiModelProperty(value = "名称",required = true)
    @NotEmpty(message = "名称不能为空")
    private String activityName;

    @ApiModelProperty(value = "关键字",required = true)
    @NotEmpty(message = "关键字不能为空")
    private String code;

    @ApiModelProperty(value = "是否回复关键字推送活动 0：不推送  1：推送",required = true)
    @NotNull(message = "是否回复关键字推送活动不能为空")
    private Integer isPush = 0;

    @ApiModelProperty(value = "活动规则",required = true)
    private String activityDesc;

    @ApiModelProperty(value = "活动开始时间")
    private Date startTime;

    @ApiModelProperty(value = "活动结束时间")
    private Date endTime;

    @ApiModelProperty(value = "是否需要手机号 0：需要  1：不需要",required = true)
    @NotNull(message = "是否需要手机号不能为空")
    private Integer needPhone = 0;

    @ApiModelProperty(value = "是否需要短信验证 0：需要  1：不需要",required = true)
    @NotNull(message = "是否需要短信验证不能为空")
    private Integer needSms = 0;

    @ApiModelProperty(value = "活动状态，0：禁用，1：启用",required = true)
    @NotNull(message = "活动状态不能为空")
    private Integer enable = 0;

    @ApiModelProperty(value = "每人总次数",required = true)
    @NotNull(message = "每人总次数不能为空")
    @Min(value = 0,message = "排序号不能小于0")
    @Max(value = 127,message = "排序号不能大于127")
    private Integer personTotalNumber = 0;

    @ApiModelProperty(value = "每人每天次数",required = true)
    @NotNull(message = "每人每天次数不能为空")
    @Min(value = 0,message = "排序号不能小于0")
    @Max(value = 127,message = "排序号不能大于127")
    private Integer personDailyNumber = 0;

    @ApiModelProperty(value = "每人每天中奖次数",required = true)
    @NotNull(message = "每人每天中奖次数不能为空")
    @Min(value = 0,message = "排序号不能小于0")
    @Max(value = 127,message = "排序号不能大于127")
    private Integer personDailyTargetNumber = 0;

    @ApiModelProperty(value = "时间间隔",required = true)
    @NotNull(message = "时间间隔不能为空")
    private Integer targetPeriod = 0;

    @ApiModelProperty(value = "参与频率",required = true)
    @NotNull(message = "参与频率不能为空")
    private Integer participationFrequency = 0;

   /* @ApiModelProperty(value = "分享标题",required = true)
    @NotEmpty(message = "分享标题不能为空")
    private String shareTitle;

    @ApiModelProperty(value = "分享图片",required = true)
    @NotEmpty(message = "分享图片不能为空")
    private String shareImg;

    @ApiModelProperty(value = "分享描述",required = true)
    @NotEmpty(message = "分享描述不能为空")
    private String shareDesc;

    @ApiModelProperty(value = "图文标题",required = true)
    @NotEmpty(message = "图文标题不能为空")
    private String imgTextTitle;

    @ApiModelProperty(value = "图文图片",required = true)
    @NotEmpty(message = "图文图片不能为空")
    private String imgTextUrl;

    @ApiModelProperty(value = "图文描述",required = true)
    @NotEmpty(message = "图文描述不能为空")
    private String imgTextDesc;*/

    /*@ApiModelProperty(value = "中奖人数",required = true)
    @NotNull(message = "中奖人数不能为空")
    private Integer targetCount = 0;

    @ApiModelProperty(value = "中奖比例",required = true)
    @NotEmpty(message = "中奖比例不能为空")
    private List<TargetRateDTO> targetRate;*/

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsPush() {
        return isPush;
    }

    public void setIsPush(Integer isPush) {
        this.isPush = isPush;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
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

    /*public String getShareTitle() {
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

   /* public Integer getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(Integer targetCount) {
        this.targetCount = targetCount;
    }

    public List<TargetRateDTO> getTargetRate() {
        return targetRate;
    }

    public void setTargetRate(List<TargetRateDTO> targetRate) {
        this.targetRate = targetRate;
    }*/

    public Activity toActivity(){
        Activity activity = new Activity();
        BeanUtils.copyProperties(this,activity);
        return  activity;
    }
}
