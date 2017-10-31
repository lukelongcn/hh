package com.h9.api.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;

import java.text.MessageFormat;
import java.util.Base64;

/**
 * Created with IntelliJ IDEA.
 * Description:微信相关信息获取
 * WeChatService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:32
 */
@Service
public class WeChatProvider {

    @Value("${wechat.js.appid}")
    private String jsAppId;
    @Value("${wechat.js.secret}")
    private String jsSecret;
    @Value("${common.wechat.callback}")
    private String url;
    @Resource
    private RestTemplate restTemplate;


    public String getJSCode(String appId,String redirectUrl, String state) {
        return MessageFormat.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=" +
                "code&scope={2}&state={3}#wechat_redirect", appId, url, "snsapi_base", state);
    }

    public String getJSCode(String appId,String state){
        if(appId == null){
            appId = jsAppId;
        }
        return getJSCode(appId,url,state);
    }

    public String getCode(String code,String state){
        byte[] decode = Base64.getDecoder().decode(state);
        return concatUrl(new String(decode),code);
    }

    public String concatUrl(String url,String code){
        StringBuffer stringBuffer = new StringBuffer(url);
        if(url.contains("?")){
            stringBuffer.append("&");
        }else{
            stringBuffer.append("?");
        }
        stringBuffer.append("code=");
        stringBuffer.append(code);
        return stringBuffer.toString();
    }


    public String getOpenIdUrl(String appId, String secret, String code){
        String openId = "";
        String url = MessageFormat.format("https://api.weixin.qq.com/" +
                "sns/oauth2/access_token" +
                "?appid={0}&secret={1}" +
                "&code={2}&grant_type=authorization_code", appId, secret, code);
        String getTokenResult = restTemplate.getForObject(url, String.class);
        restTemplate.getForObject(url, String.class);
        return openId;
    }









}
