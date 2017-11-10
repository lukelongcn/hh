package com.h9.common.db.enums;

/**
 * v币/积分流水类型枚举
 * Created by Gonyb on 2017/11/10.
 */
public enum CoinsTypeEnum {
    RED_PACKET(2,"抢红包"),//获得积分
    WIN_GOOD_CEREMONY(4,"积分赢好礼"),//获得积分
    EXCHANGE(5,"积分兑换商品");//扣除积分

    private int code;
    private String name;

    CoinsTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
