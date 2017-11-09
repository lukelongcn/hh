package com.h9.common.db.bean;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

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
    private static String smsCodeKey = "h9:sms:code:%s:type:%s";
    /**
     * description: %s 占位符为UserId
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

    public static String getSmsCodeKey(String phone,int type) {
        return String.format(smsCodeKey, phone,type);
    }

    public static void main(String[] args) {
        String smsCodeKey = getSmsCodeKey("17673140753", 1);
        System.out.println(smsCodeKey);
    }

    public static String getTokenKey(Long userId) {
        return String.format(tokenKey,userId);

    }

    public static String getAdminTokenUserIdKey(String token){
        return String.format("h9:admin:userId:%s",token);
    }

    /***
     * 获取通用全局配置
     * @see com.h9.common.db.entity.GlobalProperty
     * @param code
     * @return
     */
    public static String getConfigValue(String code){
        return MessageFormat.format("config:{0}",code);
    }
    public static String getConfigType(String code){
        return MessageFormat.format("config:{0}:type",code);
    }

}
