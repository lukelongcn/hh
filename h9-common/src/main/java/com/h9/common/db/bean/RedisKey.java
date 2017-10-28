package com.h9.common.db.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:redis的键值
 * RedisKey:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 9:31
 */
public class RedisKey {
    private static final String lastSendKey = "h9:sms:lastSend:%s";
    private static final String sendCountKey = "h9:sms:count:%s";
    private static final String smsCodeKey = "h9:sms:code:%s";

    public static String getLastSendKey(String phone) {
        if(StringUtils.isBlank(phone)) return null;
        return String.format(lastSendKey,phone);
    }

    public static String getSendCountKey(String phone) {
        if(StringUtils.isBlank(phone)) return null;
        return String.format(sendCountKey,phone);
    }

    public static String getSmsCodeKey(String phone) {
        if(StringUtils.isBlank(phone)) return null;
        return String.format(smsCodeKey,phone);
    }
}
