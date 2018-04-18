package com.h9.common.db.bean;

import com.h9.common.db.entity.config.GlobalProperty;
import com.h9.common.utils.DateUtil;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

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
    private static String smsCodeKey = "h9:sms:code:%s:type:%s";
    /**
     * description: %s 占位符为token
     */
    private static String tokenUserIdKey = "h9:userId:%s";

    /**
     * description: 短信验证码错误次数（区分类型，userId）
     */
    private static String errorCodeCountKey = "h9:sms:code:errorCount:userId:%s:type:%s";

    /**
     * description: 用户人数
     */
    public static String userCountKey = "h9:user:count:";

    public static String getUserCountKey(Date date) {
        if (date == null) return "";
        return userCountKey + DateUtil.formatDate(date, DateUtil.FormatType.MONTH);
    }

    public static String getTokenUserIdKey(String token) {
        return String.format(tokenUserIdKey, token);
    }

    public static String getWeChatUserId(String token) {
        return String.format("h9:wechat:userId:%s", token);
    }


    public static String addressKey = "h9:address:areas";

    /**
     * description: 提现次数
     */
    public static String withdrawSuccessCount = "h9:withdraw:userId_%s:count";

    public static String batchRechargeCacheId = "h9:batchRecharge:id_";

    public static String todayRechargeMoney = "h9:mobile:recharge:userId_";

    /**
     * description: 红包二维码
     */
    public static String QR_CODE = "h9:qr:code:";

    /**
     * description: 二维码对应的 临时 UUID，码和 红包二维码 共存亡
     * value 对应 userId
     */
    public static String QR_CODE_TEMP_ID = "h9:qr:code:tempId:";

    public static String getQrCodeTempId(String tempId) {
        return QR_CODE_TEMP_ID + tempId;
    }

    public static String getQrCode(Long sequenceId) {
        return QR_CODE + "" + sequenceId;
    }

    public static String getQrCode(String sequenceId) {
        return QR_CODE + "" + sequenceId;
    }

    public static String getTodayRechargeMoney(Long userId) {
        return todayRechargeMoney + userId;
    }

    public static String getBatchRechargeCacheId() {
        return batchRechargeCacheId + UUID.randomUUID().toString();
    }

    public static String getWithdrawSuccessCountKey(Long userId) {
        String format = String.format(withdrawSuccessCount, userId);
        return format;
    }

    public static void main(String[] args) {
        String smsCodeKey = getSmsCodeKey("17673140753", 1);
        System.out.println(smsCodeKey);
    }


    public static String getAdminTokenUserIdKey(String token) {
        return String.format("h9:admin:userId:%s", token);
    }

    /***
     * 获取通用全局配置
     * @see com.h9.common.db.entity.config.GlobalProperty
     * @param code
     * @return
     */
    public static String getConfigValue(String code) {
        return MessageFormat.format("config:{0}", code);
    }

    public static String getConfigType(String code) {
        return MessageFormat.format("config:{0}:type", code);
    }

    public static String wechatAccessToken = "wechat:accesstoken";
    public static String wechatTicket = "wechat:ticket";

    //短信一分钟控制
    public static String getSmsCodeCountDown(String phone, int type) {
        return MessageFormat.format("sms:code:countdown:{0}:{1}", type, phone);
    }

    //短信一分钟控制
    public static String getSmsCodeCount(String phone, int type) {
        return MessageFormat.format("sms:code:count:{0}:{1}", type, phone);
    }

    public static String getSmsCodeKey(String phone, int type) {
        return MessageFormat.format("sms:code:{0}:{1}", type, phone);
    }

    public static String getErrorCodeCountKey(Long userId, int type) {

        return String.format(errorCodeCountKey, userId, type);
    }

    public static String getLotteryBefore(Long userId, Long rewardId) {
        return String.format("lottery:%d:%d:before", userId, rewardId);
    }

    private static String uuid2couponIdKey = "send:couponId:";

    public static String getUuid2couponIdKey(String uuid) {
        return uuid2couponIdKey + uuid;
    }
}



