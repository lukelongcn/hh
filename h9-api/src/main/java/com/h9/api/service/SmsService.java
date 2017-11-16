package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.provider.SMSProvide;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigHanler;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.SMSLog;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.db.repo.SMSLogReposiroty;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * SmsService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/15
 * Time: 11:40
 */
@Service
public class SmsService {

    //    h9.sendMessage = false
    @Value("${h9.sendMessage}")
    private boolean sendMessage;
    @Resource
    private RedisBean redisBean;

    Logger logger = Logger.getLogger(SmsService.class);

    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private SMSProvide smsProvide;
    @Resource
    private ConfigHanler configHanler;
    @Resource
    private UserAccountRepository userAccountRepository;

    /**
     * description:
     *
     *  @see com.h9.api.enums.SMSTypeEnum 短信类别
     *  REGISTER(1,"注册,登录"),
     *  BIND_MOBILE(2, "绑定手机"),
     *  CASH_RECHARGE(3,"提现"),
     *  DIDI_CARD(4,"滴滴卡兑换"),
     *  MOBILE_RECHARGE(5, "手机话费充值"),
     *  OTHER(0,"其他");
     */
    public Result sendSMSCode(Long userId, String phone, int smsType) {
        SMSTypeEnum smstypeEnum = SMSTypeEnum.findByCode(smsType);
        if (smstypeEnum == null) {
            return Result.fail("短信类型不对");
        }

        //TODO 客户端调完放开
        // 把登录注册的短信移抽取出去，其他类型的短信发送都是要登录才能使用的。
//        if(smsType == SMSTypeEnum.REGISTER.getCode()){
//            return Result.fail("请求错误");
//        }

        Result result = sendSMSValdite(userId, phone, smsType);

        if (result != null) {
            return result;
        }

        //短信限制 一分钟一次lastSendKey
        String lastSendKey = RedisKey.getSmsCodeCountDown(phone, smsType);
        String lastSendValue = redisBean.getStringValue(lastSendKey);
        if (!StringUtils.isBlank(lastSendValue)) {
            return new Result(1, "短信发送过于频繁,请一分钟再试~");
        }
        //短信限制 一天最多十次
        String countKey = RedisKey.getSmsCodeCount(phone, smsType);
        String countStr = redisBean.getStringValue(countKey);

        int count = 0;
        if (!StringUtils.isEmpty(countStr)) {
            count = Integer.valueOf(countStr);
        }

        int smsOneDayCount = configHanler.getSmsOneDayCount();
        if (count > smsOneDayCount) {
            return Result.fail("短信发送过于频繁,请稍后再试~");
        }

        String code;
        String codeKey = RedisKey.getSmsCodeKey(phone, smsType);
        code = redisBean.getStringValue(codeKey);
        if (StringUtils.isEmpty(code)) {
            code = RandomStringUtils.random(4, "0123456789");
        }
        String content = getContent(smsType, code);
        Result returnMsg = null;
        logger.debugv("sendMessage=" + sendMessage);
        if (sendMessage) {
            returnMsg = smsProvide.sendSMS(phone, content);
        } else {
            code = "0000";
            returnMsg = Result.success("验证码 0000");
        }

        if (returnMsg != null && returnMsg.getCode() != 0) {
            //失败
            SMSLog smsLog = new SMSLog(content, phone, code, false);
            smsLog.setType(smsType);
            smsLogReposiroty.save(smsLog);
            return Result.fail("请稍后再试");
        } else {
            //成功
            SMSLog smsLog = new SMSLog(content, phone, code, true);
            smsLog.setType(smsType);
            smsLogReposiroty.save(smsLog);
            //短信发送成功，一分钟后才能发第二条
            redisBean.setStringValue(lastSendKey, System.currentTimeMillis() + "", 60, TimeUnit.SECONDS);
            //发送次数加1,1天超时
            redisBean.setStringValue(countKey, (count + 1) + "");
            redisBean.expire(countKey, DateUtil.getTimesNight());
            //存储短信记录
            redisBean.setStringValue(codeKey, code, 10, TimeUnit.MINUTES);
            return Result.success("短信发送成功");
        }
    }

    private String getContent(int type, String code) {
        return "您的校验码是：" + code + "。请不要把校验码泄露给其他人。如非本人操作，可不用理会！";
    }


    private UserRepository userRepository;


    //校验信息
    private Result sendSMSValdite(Long userId, String phone, int smsType) {

        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        BigDecimal balance = userAccount.getBalance();

        if (balance.compareTo(new BigDecimal(0)) <= 0) {
            return Result.fail("余额不足");
        }
        return null;
    }


    @SuppressWarnings("Duplicates")
    public Result sendSMSCode(String phone) {

        //短信限制 一分钟一次lastSendKey
        String lastSendKey = RedisKey.getSmsCodeCountDown(phone, SMSTypeEnum.REGISTER.getCode());
        String lastSendValue = redisBean.getStringValue(lastSendKey);
        if (!StringUtils.isBlank(lastSendValue)) {
            return new Result(1, "短信发送过于频繁,请一分钟再试~");
        }
        //短信限制 一天最多十次
        String countKey = RedisKey.getSmsCodeCount(phone, SMSTypeEnum.REGISTER.getCode());
        String countStr = redisBean.getStringValue(countKey);

        int count = 0;
        if (!StringUtils.isEmpty(countStr)) {
            count = Integer.valueOf(countStr);
        }

        int smsOneDayCount = configHanler.getSmsOneDayCount();
        if (count > smsOneDayCount) {
            return Result.fail("短信发送过于频繁,请稍后再试~");
        }

        String code;
        String codeKey = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.REGISTER.getCode());
        code = redisBean.getStringValue(codeKey);
        if (StringUtils.isEmpty(code)) {
            code = RandomStringUtils.random(4, "0123456789");
        }
        String content = getContent(SMSTypeEnum.REGISTER.getCode(), code);
        Result returnMsg = null;
        logger.debugv("sendMessage=" + sendMessage);
        if (sendMessage) {
            returnMsg = smsProvide.sendSMS(phone, content);
        } else {
            code = "0000";
            returnMsg = Result.success("验证码 0000");
        }

        if (returnMsg != null && returnMsg.getCode() != 0) {
            //失败
            SMSLog smsLog = new SMSLog(content, phone, code, false);
            smsLog.setType(SMSTypeEnum.REGISTER.getCode());
            smsLogReposiroty.save(smsLog);
            return Result.fail("请稍后再试");
        } else {
            //成功
            SMSLog smsLog = new SMSLog(content, phone, code, true);
            smsLog.setType(SMSTypeEnum.REGISTER.getCode());
            smsLogReposiroty.save(smsLog);
            //短信发送成功，一分钟后才能发第二条
            redisBean.setStringValue(lastSendKey, System.currentTimeMillis() + "", 60, TimeUnit.SECONDS);
            //发送次数加1,1天超时
            redisBean.setStringValue(countKey, (count + 1) + "");
            redisBean.expire(countKey, DateUtil.getTimesNight());
            //存储短信记录
            redisBean.setStringValue(codeKey, code, 10, TimeUnit.MINUTES);
            return Result.success("短信发送成功");
        }

    }
}
