package com.h9.admin.model.vo;

import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.Reward;
import com.h9.common.db.entity.UserRecord;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author: George
 * @date: 2017/11/8 13:52
 */
public class LotteryFlowVO {
    @ApiModelProperty(value = "id" )
    private Long id;

    @ApiModelProperty(value = "用户id" )
    private Long userId;

    @ApiModelProperty(value = "兑奖码" )
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "金额" )
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value = "经度" )
    private double longitude;

    @ApiModelProperty(value = "纬度" )
    private double latitude;

    @ApiModelProperty(value = "ip地址" )
    private String ip;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static LotteryFlowVO toLotteryFlowVO(LotteryFlow lotteryFlow){
        LotteryFlowVO lotteryFlowVO = new LotteryFlowVO();
        BeanUtils.copyProperties(lotteryFlow,lotteryFlow);
        lotteryFlowVO.setIp(lotteryFlow.getUserRecord().getIp());
        lotteryFlowVO.setLatitude(lotteryFlow.getUserRecord().getLatitude());
        lotteryFlowVO.setLongitude(lotteryFlow.getUserRecord().getLongitude());
        lotteryFlowVO.setUserId(lotteryFlow.getUser().getId());
        return  lotteryFlowVO;
    }
}
