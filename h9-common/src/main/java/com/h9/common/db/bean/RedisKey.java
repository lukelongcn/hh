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
    /**
     * description: %s 占位符为手机号码
     */
    private static String lastSendKey = "h9:sms:lastSend:%s";
    /**
     * description: %s 占位符为手机号码
     */
    private static String sendCountKey = "h9:sms:count:%s";
    /**
     * description: %s 占位符为手机号码
     */
    private static String smsCodeKey = "h9:sms:code:%s";
    /**
     * description: %s 占位符为手机号码
     */
    private static String tokenKey ="h9:token:%s";
    /**
     * description: %s 占位符为token
     */
    private static String tokenUserIdKey = "h9:userId:%s";
    /**
     * description: %s 占位符为token
     */
    public static final String adminTokenUserIdKey = "h9:admin:userId:%s";

    public static String getTokenUserIdKey(String token){
        return String.format(tokenUserIdKey,token);
    }
    public static String getLastSendKey(String phone) {
        return String.format(lastSendKey, phone);
    }

    public static String getSendCountKey(String phone) {
        return String.format(sendCountKey, phone);
    }

    public static String getSmsCodeKey(String phone) {
        return String.format(smsCodeKey, phone);
    }

    public static String getTokenKey(String phone) {
        return String.format(tokenKey,phone);
    }

    public static String getAdminTokenUserIdKey(String token){
        return String.format(adminTokenUserIdKey,token);
    }
}
