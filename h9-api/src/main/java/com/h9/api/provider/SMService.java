package com.h9.api.provider;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.entity.SMSLog;
import com.h9.common.db.repo.SMSLogReposiroty;
import com.h9.common.utils.MD5Util;
import com.mysql.jdbc.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2017/10/27.
 */

@Component
public class SMService {

    private static final String smsUrl = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
    private static final String appId = "cf_tianlitai";
    private static final String appKey = "1a4772f6b6eee0a3600921a56d13e139";
    private static final String lastSendKey = "h9:sms:lastSend:%s";
    private static final String sendCountKey = "h9:sms:count:%s";
    public static final String smsCodeKey = "h9:sms:code:%s";

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private RedisBean redisBean;

    /**
     * description:发送短信 一分钟一次 / 一天最多十次
     */
    public Result sendSMS(String mobile) {

        if (StringUtils.isBlank(mobile)) return new Result(1, "请提供手机号");

        String time = String.valueOf(new Date().getTime()).substring(0, 10);
        String code = RandomStringUtils.random(4, "0123456789");
        String content = "您的校验码是：" + code + "。请不要把校验码泄露给其他人。如非本人操作，可不用理会！";
        String pwd = MD5Util.getMD5(appId + appKey + mobile + content + time);

        URI uri = UriComponentsBuilder.fromHttpUrl(smsUrl)
                .queryParam("account", appId)
                .queryParam("password", pwd)
                .queryParam("mobile", mobile)
                .queryParam("content", content)
                .queryParam("time", time)
                .queryParam("format", "json")
                .build().toUri();

        //短信限制 一分钟一次
        String key = String.format(lastSendKey, mobile);
        String value = redisBean.getStringValue(key);
        if (!StringUtils.isBlank(value)) {
            return new Result(1, "系统繁忙,请稍后再试~");
        }
        //短信限制 一天最多十次
        String countKey = String.format(sendCountKey, mobile);
        String countStr = redisBean.getStringValue(countKey);
        int count = 0;
        if (countStr != null) {
            try {
                count = Integer.valueOf(countStr);
                if (count > 9) return new Result(1, "系统繁忙,请稍后再试~");
            } catch (NumberFormatException e) {
                logger.info(e.getMessage(), e);
                redisBean.setStringValue(countKey, 0 + "", 1, TimeUnit.DAYS);
            }
        } else {
            redisBean.setStringValue(countKey, 0 + "", 1, TimeUnit.DAYS);
        }

        logger.info("今天已发送次: "+count);
//        String res = restTemplate.getForObject(uri, String.class);
//        ReturnMsg returnMsg = JSONObject.parseObject(res, ReturnMsg.class);
        ReturnMsg returnMsg = new ReturnMsg();
        returnMsg.setCode(2);
        returnMsg.setMsg("成功");
        returnMsg.setSmsid("0");
        //处理结果
        if (returnMsg != null && returnMsg.getCode() != 2) {
            //失败
            SMSLog smsLog = new SMSLog(content, mobile, false);
            smsLogReposiroty.save(smsLog);
            return new Result(1, returnMsg.getMsg());
        } else {
            //成功
            SMSLog smsLog = new SMSLog(content, mobile, true);
            smsLogReposiroty.save(smsLog);
            redisBean.setStringValue(key, System.currentTimeMillis() + "", 60, TimeUnit.SECONDS);
            //发送次数加1,1天超时
            redisBean.setStringValue(countKey, ((++count))+"",1,TimeUnit.DAYS);
            String codeKey = String.format(smsCodeKey, mobile);
            logger.info("用户:"+mobile+" code:"+code);
            redisBean.setStringValue(codeKey, code, 10, TimeUnit.MINUTES);
            return new Result(0, returnMsg.getMsg());
        }
    }

    private static class ReturnMsg {
        private int code;
        private String msg;
        private String smsid;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getSmsid() {
            return smsid;
        }

        public void setSmsid(String smsid) {
            this.smsid = smsid;
        }

        @Override
        public String toString() {
            return "returnMsg{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", smsid='" + smsid + '\'' +
                    '}';
        }
    }
}
