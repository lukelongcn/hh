package com.h9.api.model.dto;

import java.util.UUID;

/**
 * Created by lmh on 2017/6/13.
 */
public class WechatConfig {

    private String appId;
    private String timestamp = genTimeStamp();
    private String nonceStr = UUID.randomUUID().toString().replace("-", "");;
    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public static String genTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
