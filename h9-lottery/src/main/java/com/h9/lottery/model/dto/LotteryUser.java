package com.h9.lottery.model.dto;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * LotteryFlowDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/3
 * Time: 10:27
 */
public class LotteryUser {
    private Long userId;
    private String name;
    private String avatar;
    private boolean me;
    private boolean roomUser;
    private BigDecimal money = new BigDecimal(0);
    private String desc;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isRoomUser() {
        return roomUser;
    }

    public void setRoomUser(boolean roomUser) {
        this.roomUser = roomUser;
    }

}

