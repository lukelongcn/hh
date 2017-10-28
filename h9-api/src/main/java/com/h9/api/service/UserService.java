package com.h9.api.service;

import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.provider.SMService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.SMSLog;
import com.h9.common.utils.MD5Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2017/10/26.
 */
@Service
@Transactional
public class UserService {
    @Resource
    private RedisBean redisBean;
    @Resource
    private SMService smService;

    public Result loginFromPhone(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        String code = userLoginDTO.getCode();

        String redisCode = redisBean.getStringValue(String.format(RedisKey.getSmsCodeKey(phone), phone));
        if (!code.equals(redisCode)) return Result.fail("验证码不在确");

        return Result.success();
    }

//    public Result smsRegister(String phone) {
//
//        String code = RandomStringUtils.random(4, "0123456789");
//        String time = String.valueOf(new Date().getTime()).substring(0, 10);
//        String content = "您的校验码是：" + code + "。请不要把校验码泄露给其他人。如非本人操作，可不用理会！";
//        String pwd = MD5Util.getMD5(SMService.appId + SMService.appKey + phone + content + time);
//
//        URI uri = UriComponentsBuilder.fromHttpUrl(SMService.smsUrl)
//                .queryParam("account", SMService.appId)
//                .queryParam("password", pwd)
//                .queryParam("mobile", phone)
//                .queryParam("content", content)
//                .queryParam("time", time)
//                .queryParam("format", "json")
//                .build().toUri();
//
//        //短信限制 一分钟一次lastSendKey
//        String key = String.format(RedisKey.getLastSendKey(phone), phone);
//        String value = redisBean.getStringValue(key);
//        if (!StringUtils.isBlank(value)) {
//            return new Result(1, "系统繁忙,请稍后再试~");
//        }
//        //短信限制 一天最多十次
//        String countKey = String.format(sendCountKey, mobile);
//        String countStr = redisBean.getStringValue(countKey);
//        int count = 0;
//        if (countStr != null) {
//            try {
//                count = Integer.valueOf(countStr);
//                if (count > 9) return new Result(1, "系统繁忙,请稍后再试~");
//            } catch (NumberFormatException e) {
//                logger.info(e.getMessage(), e);
//                redisBean.setStringValue(countKey, 0 + "", 1, TimeUnit.DAYS);
//            }
//        } else {
//            redisBean.setStringValue(countKey, 0 + "", 1, TimeUnit.DAYS);
//        }
//
//        logger.info("今天已发送次: " + count);
////        String res = restTemplate.getForObject(uri, String.class);
////        ReturnMsg returnMsg = JSONObject.parseObject(res, ReturnMsg.class);
//        SMService.ReturnMsg returnMsg = new SMService.ReturnMsg();
//        returnMsg.setCode(2);
//        returnMsg.setMsg("成功");
//        returnMsg.setSmsid("0");
//        //处理结果
//        if (returnMsg != null && returnMsg.getCode() != 2) {
//            //失败
//            SMSLog smsLog = new SMSLog(content, mobile, false);
//            smsLogReposiroty.save(smsLog);
//            return new Result(1, returnMsg.getMsg());
//        } else {
//            //成功
//            SMSLog smsLog = new SMSLog(content, mobile, true);
//            smsLogReposiroty.save(smsLog);
//            redisBean.setStringValue(key, System.currentTimeMillis() + "", 60, TimeUnit.SECONDS);
//            //发送次数加1,1天超时
//            redisBean.setStringValue(countKey, ((++count)) + "", 1, TimeUnit.DAYS);
//            String codeKey = String.format(smsCodeKey, mobile);
//            logger.info("用户:" + mobile + " code:" + code);
//            redisBean.setStringValue(codeKey, code, 10, TimeUnit.MINUTES);
//            return new Result(0, returnMsg.getMsg());
//        }
//
//        return smService.sendSMS(phone);
//    }




}
