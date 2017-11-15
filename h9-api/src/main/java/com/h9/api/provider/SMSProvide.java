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
public class SMSProvide {

    public static final String smsUrl = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
    public static final String appId = "cf_tianlitai";
    public static final String appKey = "1a4772f6b6eee0a3600921a56d13e139";

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private RedisBean redisBean;


    public Result sendSMS(String mobile,String content) {

        if (StringUtils.isBlank(mobile)) return new Result(1, "请提供手机号");

        String time = String.valueOf(new Date().getTime()).substring(0, 10);
        String pwd = MD5Util.getMD5(appId + appKey + mobile + content + time);

        URI uri = UriComponentsBuilder.fromHttpUrl(smsUrl)
                .queryParam("account", appId)
                .queryParam("password", pwd)
                .queryParam("mobile", mobile)
                .queryParam("content", content)
                .queryParam("time", time)
                .queryParam("format", "json")
                .build().toUri();

        ReturnMsg returnMsg = restTemplate.getForObject(uri, ReturnMsg.class);
        //处理结果
        if (returnMsg != null && returnMsg.getCode() != 2) {
            return new Result(1, returnMsg.getMsg());
        } else {
            return new Result(0, returnMsg.getMsg());
        }
    }

    public static class ReturnMsg {
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
