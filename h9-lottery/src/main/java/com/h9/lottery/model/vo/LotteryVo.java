package com.h9.lottery.model.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryVo:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/3
 * Time: 15:26
 */
public class LotteryVo {
    @NotEmpty(message = "条码不存在")
    private String code;
    private double longitude;
    private double latitude;
    private String version;
    private Integer client;
    private String imei;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}