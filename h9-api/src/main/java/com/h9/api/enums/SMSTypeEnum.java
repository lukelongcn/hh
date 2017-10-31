package com.h9.api.enums;

/**
 * Created by itservice on 2017/10/31.
 */
public enum SMSTypeEnum {

    REGISTER(1,"注册"),
    BIND_MOBILE(2, "绑定手机"),
    /**
     * description: 这类短信发送没有限制
     */
    OTHER(-1,"其他");

    SMSTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
