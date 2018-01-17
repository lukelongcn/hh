package com.h9.api.model.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * ClientConfigDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/17
 * Time: 15:44
 */
@Data
@Component
public class ClientConfig {
    @Value("${client.pay.appId}")
    private String appId;
    @Value("${client.pay.appSecret}")
    private String appSecret;
    @Value("${client.pay.mchId}")
    private String mchId;
    @Value("${client.pay.apiKey}")
    private String apiKey;


}
