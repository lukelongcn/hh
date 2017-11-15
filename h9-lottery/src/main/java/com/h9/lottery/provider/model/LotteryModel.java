package com.h9.lottery.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * LotteryModel:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 18:15
 */
public class LotteryModel {
    @JsonProperty("State")
    private int State;
    @JsonProperty("Msg")
    private String Msg;
    @JsonProperty("Bouns")
    private BigDecimal Bouns = new BigDecimal(0);
    @JsonProperty("SeedAmount")
    private BigDecimal SeedAmount = new BigDecimal(0);
    @JsonProperty("Intergal")
    private BigDecimal Intergal = new BigDecimal(0);

    /****State
     0：获取兑奖码对应奖金成功
     1：获取兑奖码对应积分成功
     2：兑奖码已兑奖
     3：兑奖码不存在
     4：接口调用错误
     */
    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public BigDecimal getBouns() {
        return Bouns;
    }

    public void setBouns(BigDecimal bouns) {
        Bouns = bouns;
    }

    public BigDecimal getSeedAmount() {
        return SeedAmount;
    }

    public void setSeedAmount(BigDecimal seedAmount) {
        SeedAmount = seedAmount;
    }

    public BigDecimal getIntergal() {
        return Intergal;
    }

    public void setIntergal(BigDecimal intergal) {
        Intergal = intergal;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
