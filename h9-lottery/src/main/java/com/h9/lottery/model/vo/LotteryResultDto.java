package com.h9.lottery.model.vo;

/**
 * Created with IntelliJ IDEA.
 *
 * LotteryResultDto:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 16:01
 */
public class LotteryResultDto {
    private boolean lottery;
    private boolean roomUser;

    public boolean isLottery() {
        return lottery;
    }

    public void setLottery(boolean lottery) {
        this.lottery = lottery;
    }

    public boolean isRoomUser() {
        return roomUser;
    }

    public void setRoomUser(boolean roomUser) {
        this.roomUser = roomUser;
    }
}
