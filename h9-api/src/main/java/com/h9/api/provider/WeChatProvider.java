package com.h9.api.provider;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.WechatConfig;
import com.h9.api.provider.model.OpenIdCode;
import com.h9.api.provider.model.WeChatUser;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:微信相关信息获取
 * WeChatService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:32
 */
@Service
public class WeChatProvider {

    Logger logger = Logger.getLogger(WeChatProvider.class);

    @Value("${wechat.js.appid}")
    private String jsAppId;
    @Value("${wechat.js.secret}")
    private String jsSecret;
    @Value("${common.wechat.callback}")
    private String url;
    @Resource
    private RestTemplate restTemplate;


    public String getJSCode(String appId, String redirectUrl, String state) {
        String realUrl = "";
        try {
            realUrl =  URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MessageFormat.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=" +
                "code&scope={2}&state={3}#wechat_redirect", appId ,realUrl, "snsapi_userinfo", state);  //snsapi_base
    }

    public String getJSCode(String appId, String state) {
        if (appId == null) {
            appId = jsAppId;
        }
        String jsCode = getJSCode(appId, url, state);
        logger.debugv(jsCode);
        return jsCode;
    }

    public String getCode(String code, String state) {
        byte[] decode = Base64.getDecoder().decode(state);
        String url = concatUrl(new String(decode), code);
        logger.debugv(url);
        return url;
    }

    public String concatUrl(String url, String code) {
        StringBuffer stringBuffer = new StringBuffer(url);
        if (url.contains("?")) {
            stringBuffer.append("&");
        } else {
            stringBuffer.append("?");
        }
        stringBuffer.append("code=");
        stringBuffer.append(code);
        return stringBuffer.toString();
    }


    public OpenIdCode getOpenId(String appId, String secret, String code) {
        try {
            String url = MessageFormat.format("https://api.weixin.qq.com/" +
                    "sns/oauth2/access_token" +
                    "?appid={0}&secret={1}" +
                    "&code={2}&grant_type=authorization_code", appId, secret, code);
            OpenIdCode openIdCode = restTemplate.getForObject(url, OpenIdCode.class);
            logger.debug(JSONObject.toJSONString(openIdCode));
            return openIdCode;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    public WeChatUser getUserInfo(OpenIdCode openIdCode) {
        try {
            String url = MessageFormat.format("https://api.weixin.qq.com/sns/userinfo" +
                            "?access_token={0}&openid={1}&lang=zh_CN",
                    openIdCode.getAccess_token(), openIdCode.getOpenid());
            WeChatUser weChatUser = restTemplate.getForObject(url, WeChatUser.class);
            logger.debug(JSONObject.toJSONString(weChatUser));
            return weChatUser;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }



    public  String getTicketTokenUrl() {
        return MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", jsAppId, jsSecret);
    }

    public String getTicketUrl(String token) {
        return MessageFormat.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi", token);
    }

    @Resource
    private RedisBean redisBean;

    public String getWeChatAccessToken() {
        String access_token = redisBean.getStringValue(RedisKey.wechatAccessToken);
        if (!StringUtils.isEmpty(access_token)) {
            return access_token;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            String accessTokenReponse = restTemplate.getForObject(getTicketTokenUrl(), String.class);
            Map<String, Object> map = JSONObject.parseObject(accessTokenReponse, Map.class);
            access_token = (String) map.get("access_token");
            Integer expires_in = (Integer) map.get("expires_in");
            if (!StringUtils.isEmpty(access_token)) {
                redisBean.setStringValue(RedisKey.wechatAccessToken, access_token, expires_in - 60, TimeUnit.SECONDS);
                return access_token;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("微信获取access_token失败", ex);
        }
        return null;
    }


    public String getTicket() {
        String ticket = redisBean.getStringValue(RedisKey.wechatTicket);
        if (!StringUtils.isEmpty(ticket)) {
            return ticket;
        }
        String accessToken = getWeChatAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return accessToken;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            String accessTokenReponse = restTemplate.getForObject(getTicketUrl(accessToken), String.class);
            Map<String, Object> map = JSONObject.parseObject(accessTokenReponse, Map.class);
            ticket = (String) map.get("ticket");
            Integer expires_in = (Integer) map.get("expires_in");
            if (!StringUtils.isEmpty(ticket)) {
                redisBean.setStringValue(RedisKey.wechatTicket, ticket, expires_in - 60, TimeUnit.SECONDS);
                return ticket;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("微信获取ticket失败", ex);
        }
        return null;
    }



    public Result<WechatConfig> getConfig(String url) {
        WechatConfig config = new WechatConfig();
        String ticket = getTicket();
        config.setSignature(getSHA1(ticket, config.getTimestamp(), config.getNonceStr(), url));
        config.setAppId(jsAppId);
        return Result.success(config);
    }

    /**
     * 用SHA1算法生成安全签名
     *
     * @param ticket    票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param url       微信分享本地链接
     * @return 安全签名
     * @throws
     */
    public static String getSHA1(String ticket, String timestamp, String nonce, String url) {
        try {
            StringBuffer sb = new StringBuffer();
            String str = sb
                    .append("jsapi_ticket=")
                    .append(ticket)
                    .append("&noncestr=")
                    .append(nonce)
                    .append("&timestamp=")
                    .append(timestamp)
                    .append("&url=")
                    .append(url)
                    .toString();
            // SHA1签名生成
            return DigestUtils.sha1DigestAsHex(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }









}
