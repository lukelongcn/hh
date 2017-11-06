package com.h9.lottery.model.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryResult:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/3
 * Time: 10:26
 */
public class LotteryResult {

    private String qrCode;
    private String code;
    private boolean lottery;
    private BigDecimal money = new BigDecimal(0);
    private List<LotteryUser> users;
    private List<LotteryUser> lotteryUsers;
    private String endTime;
    private String nowTime;
    private int status;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public List<LotteryUser> getUsers() {
        return users;
    }

    public void setUsers(List<LotteryUser> users) {
        this.users = users;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public List<LotteryUser> getLotteryUsers() {
        return lotteryUsers;
    }

    public void setLotteryUsers(List<LotteryUser> lotteryUsers) {
        this.lotteryUsers = lotteryUsers;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public boolean isLottery() {
        return lottery;
    }

    public void setLottery(boolean lottery) {
        this.lottery = lottery;
    }
}
