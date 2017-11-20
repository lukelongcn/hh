package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.provider.SMSProvide;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigHanler;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.SMSLog;
import com.h9.common.db.entity.User;
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
 * Description:
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
     * @see com.h9.api.enums.SMSTypeEnum 短信类别
     * REGISTER(1,"注册,登录"),
     * BIND_MOBILE(2, "绑定手机"),
     * CASH_RECHARGE(3,"提现"),
     * DIDI_CARD(4,"滴滴卡兑换"),
     * MOBILE_RECHARGE(5, "手机话费充值"),
     * OTHER(0,"其他");
     */
    public Result sendSMSCode(Long userId, String phone, int smsType) {

        String tempPhone = userRepository.findOne(userId).getPhone();
        if (StringUtils.isNotBlank(tempPhone)) {
            phone = tempPhone;
        }
        SMSTypeEnum smstypeEnum = SMSTypeEnum.findByCode(smsType);
        if (smstypeEnum == null) {
            return Result.fail("短信类型不对");
        }

        // 把登录注册的短信移抽取出去，其他类型的短信发送都是要登录才能使用的。
        if (smsType == SMSTypeEnum.REGISTER.getCode()) {
            return Result.fail("此接口此失效");
        }

        User user = userRepository.findOne(userId);
        Result result = sendSMSValdite(userId, user.getPhone(), smsType);

        if (result != null) {
            return result;
        }

        //短信限制 一分钟一次lastSendKey
        String lastSendKey = RedisKey.getSmsCodeCountDown(phone, smsType);
        String lastSendValue = redisBean.getStringValue(lastSendKey);
        if (!StringUtils.isBlank(lastSendValue)) {
            return new Result(1, "短信发送过于频繁,请一分钟后再试~");
        }

        //错误次数大于最大次数限制
        boolean erroResult = verifyErrorCountIsMax(userId, smsType);
        if(erroResult){
            return Result.fail("您的验证码错误已达到最大错误次数，请稍后再试");
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


    @Resource
    private UserRepository userRepository;


    //校验信息
    private Result sendSMSValdite(Long userId, String phone, int smsType) {


        if (smsType != SMSTypeEnum.BIND_MOBILE.getCode()) {

            UserAccount userAccount = userAccountRepository.findByUserId(userId);
            BigDecimal balance = userAccount.getBalance();

            if (balance.compareTo(new BigDecimal(0)) <= 0) {
                return Result.fail("余额不足");
            }
        }


        return null;
    }


    /**
     * description: 发送注册验证码
     */
    @SuppressWarnings("Duplicates")
    public Result sendSMSCode(String phone) {

        //
        //短信限制 一分钟一次lastSendKey
        String lastSendKey = RedisKey.getSmsCodeCountDown(phone, SMSTypeEnum.REGISTER.getCode());
        String lastSendValue = redisBean.getStringValue(lastSendKey);
        if (!StringUtils.isBlank(lastSendValue)) {
            return new Result(1, "短信发送过于频繁,请一分钟后再试~");
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

    /**
     * description: 验证用户输入验证码错误最大次数
     *
     * @return true 达到最大次数
     */
    public boolean verifyErrorCountIsMax(Long userId, int type) {

        String errorCodeCountKey = RedisKey.getErrorCodeCountKey(userId, type);

        String errorCountStr = redisBean.getStringValue(errorCodeCountKey);
        int errorCount = 0;
        if (StringUtils.isBlank(errorCountStr)) {
            errorCount = 0;
        }else{
            errorCount = Integer.valueOf(errorCountStr);
        }

        logger.info("用户Id: " + userId + " ,错误次数: " + errorCount);
        return errorCount >= 3;

    }

    /**
     * description:
     * 验证，短信验证码
     * 设置用户指定类型验证码的错误次数 +1
     *
     * @return Result
     */
    public Result verifySmsCodeByType(Long userId, int type, String tel, String code) {

        // 验证短信
        String key = RedisKey.getSmsCodeKey(tel, type);
        String codeValue = redisBean.getStringValue(key);

        Result result = new Result();
        if (StringUtils.isBlank(codeValue) || !codeValue.equals(code)  ) {
            //
            result.setCode(1);
            result.setMsg("验证码不正确");

            String errorCodeCountKey = RedisKey.getErrorCodeCountKey(userId, type);
            String errorCountStr = redisBean.getStringValue(errorCodeCountKey);

            int errorCount = 0;

            if (errorCountStr != null) {
                errorCount = Integer.valueOf(errorCountStr);
            }

            if (errorCount >= 2) {
                result.setCode(3);
                result.setMsg("错误次数已达到最大次数,请稍后再试");

                return result;
            }

            errorCount++;
            redisBean.setStringValue(errorCodeCountKey, String.valueOf(errorCount), 10, TimeUnit.MINUTES);
            return result;
        }

        return null;
    }
}
