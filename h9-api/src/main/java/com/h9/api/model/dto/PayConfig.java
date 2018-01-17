package com.h9.api.model.dto;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * PayConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 17:22
 */
@Data
@Component
public class PayConfig {


    @Value("${wechat.js.appid}")
    private String appId = "";
    @Value("${wechat.js.secret}")
    private String appSecret= "";
    @Value("${wechat.pay.mchId}")
    private String mchId= "";
    @Value("${wechat.pay.apikey}")
    private String apiKey= "";

    private String body= "徽酒";

    private Boolean enableCreditCart= false;
    @Value("${pay.notifyUrl}")
    private String notifyUrl= "";
    @Value("${pay.callbackUrl}")
    private String callbackUrl= "";
    @Value("${pay.businessAppId}")
    private String businessAppId= "";
    @Resource
    private ClientConfig clientConfig;

}
