package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.provider.SMSProvide;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigHanler;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.SMSLog;
import com.h9.common.db.repo.SMSLogReposiroty;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private String sendMessage;
    @Resource
    private RedisBean redisBean;

    Logger logger = Logger.getLogger(SmsService.class);

    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private SMSProvide smsProvide;
    @Resource
    private ConfigHanler configHanler;

    /**
     * description:
     *
     * @see com.h9.api.enums.SMSTypeEnum 短信类别
     */
    public Result sendSMSCode(Long userId,String phone, int smsType) {
        SMSTypeEnum smstypeEnum = SMSTypeEnum.findByCode(smsType);
        if (smstypeEnum == null) {
            return Result.fail("短信类型不对");
        }
        Result result = sendSMSValdite(userId,phone, smsType);
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
        if("true".equals(sendMessage)){
            returnMsg = smsProvide.sendSMS(phone, content);
        }else{
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
    private Result sendSMSValdite(Long userId,String phone, int smsType) {

        if (smsType == SMSTypeEnum.CASH_RECHARGE.getCode()) {


        } else if (smsType == SMSTypeEnum.DIDI_CARD.getCode()) {

        } else if (smsType == SMSTypeEnum.MOBILE_RECHARGE.getCode()) {

        }
        return null;
    }



}
