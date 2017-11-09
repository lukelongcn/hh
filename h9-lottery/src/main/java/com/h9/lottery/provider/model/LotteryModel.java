package com.h9.lottery.provider.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryModel:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 18:15
 */
public class LotteryModel {
    private int State;
    private String Msg;
    private BigDecimal Bouns = new BigDecimal(0);
    private BigDecimal SeedAmount = new BigDecimal(0);
    private BigDecimal Intergal = new BigDecimal(0);


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
