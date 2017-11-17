package com.h9.lottery.model.vo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * LotteryDto:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/3
 * Time: 15:26
 */
public class LotteryDto {
    @NotBlank(message = "条码不存在")
    private String code;
    private double longitude;
    private double latitude;

    public String getCode() {
        if(code.contains("1h9.cc/")){
           return code.replace("1h9.cc/", "");
        }
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

}
