package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
public class ImeiUserRecordVO {
    @ApiModelProperty(value = "串号")
    private String imei;
    @ApiModelProperty(value = "关联账号数")
    private Long relevanceCount;
    @ApiModelProperty(value = "参与次数")
    private Long joinCount;


    public ImeiUserRecordVO() {
    }

    public ImeiUserRecordVO(String imei, Long joinCount) {
        this.imei = imei;
        this.joinCount = joinCount;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getRelevanceCount() {
        return relevanceCount;
    }

    public void setRelevanceCount(Long relevanceCount) {
        this.relevanceCount = relevanceCount;
    }

    public Long getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Long joinCount) {
        this.joinCount = joinCount;
    }
}