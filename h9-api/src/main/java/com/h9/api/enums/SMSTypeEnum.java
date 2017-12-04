package com.h9.api.enums;

/**
 * description: 1.登录/注册 2.提现 3.话费充值 4.滴滴券兑换 5.手机绑定
 */
public enum SMSTypeEnum {

    REGISTER(1,"注册,登录"),
    BIND_MOBILE(2, "绑定手机"),
    CASH_RECHARGE(3,"提现"),
    DIDI_CARD(4,"滴滴卡兑换"),
    MOBILE_RECHARGE(5, "手机话费充值"),
    BIND_BANKCARD(6, "绑定银行卡"),
    OTHER(0,"其他");

    SMSTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;


    public static SMSTypeEnum findByCode(int code){
        SMSTypeEnum[] values = values();
        for(SMSTypeEnum smsTypeEnum: values){
            if(code == smsTypeEnum.getCode()){
                return smsTypeEnum;
            }
        }
        return null;
    }

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
