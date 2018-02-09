package com.h9.api.provider;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.google.gson.JsonObject;
import com.h9.api.model.dto.MenuDTO;
import com.h9.api.model.dto.TicketDTO;
import com.h9.api.model.dto.WechatConfig;
import com.h9.api.provider.model.OpenIdCode;
import com.h9.api.provider.model.TemplateDTO;
import com.h9.api.provider.model.WeChatUser;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
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
    @Value("${path.app.wechat_host}")
    private String host;
    @Resource
    private RestTemplate restTemplate;


    public String getJSCode(String appId, String redirectUrl, String state) {
        String realUrl = "";
        try {
            realUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MessageFormat.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=" +
                "code&scope={2}&state={3}#wechat_redirect", appId, realUrl, "snsapi_userinfo", state);  //snsapi_base
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


    public String getTicketTokenUrl() {
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


    /**
     * description:  getTicket
     */
    public String getCodeTicket(String accessToken, Long userId) {

        RestTemplate restTemplate = new RestTemplate();
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setAction_name("QR_SCENE");

        //一天失效
        ticketDTO.setExpire_seconds(86400);
        TicketDTO.ActionInfoBean actionInfoBean = new TicketDTO.ActionInfoBean();
        TicketDTO.ActionInfoBean.SceneBean sceneBean = new TicketDTO.ActionInfoBean.SceneBean();
        sceneBean.setScene_id(userId.intValue());

        actionInfoBean.setScene(sceneBean);
        ticketDTO.setAction_info(actionInfoBean);

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
        String json = JSONObject.toJSONString(ticketDTO);
        logger.info(json);
        String body = null;
        try {
            body = restTemplate.postForEntity(url, json, String.class).getBody();
        } catch (RestClientException e) {
            logger.info(e.getMessage(), e);
            logger.info("获取ticket失败");
            return null;
        }
        logger.info("result: " + body);
//        Map<String,String> mapRes = JSONObject.parseObject(body,Map.class);
        Result4wx result4wx = JSONObject.parseObject(body, Result4wx.class);

        if (result4wx.getErrcode().equals("40001")) {
            logger.info("accessToken 无效，重新获取");
            getWeChatAccessToken(true);
        }

        return result4wx.getTicket();
    }


    public String getWeChatAccessToken(Boolean flush) {
        if (!flush) {
            return getWeChatAccessToken();
        } else {
            redisBean.setStringValue(RedisKey.wechatAccessToken, "", 1, TimeUnit.MILLISECONDS);
            return getWeChatAccessToken();
        }
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


    public void createMenu() {
        MenuDTO.MenuDTOBuilder builder = MenuDTO.builder();
        MenuDTO menuDTO = builder
                .button(Arrays.asList(
                        new MenuDTO.ButtonBean()
                                .setType("view")
                                .setKey("12")
                                .setUrl(host + "/h9-weixin/#/active/hongbao")
                                .setName("开盖扫红包"),
                        new MenuDTO.ButtonBean()
                                .setType("view")
                                .setKey("23")
                                .setUrl(host + "/h9-weixin/#/shop")
                                .setName("徽酒商城"),
                        new MenuDTO.ButtonBean()
                                .setType("view")
                                .setKey("31")
                                .setUrl(host + "/h9-weixin/#/travel")
                                .setName("旅游健康卡")
                        )

                ).build();

        String accessToken = getWeChatAccessToken();
        logger.info("accessToken : " + accessToken);
        String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;

        String json = JSONObject.toJSONString(menuDTO);
        logger.info("request json : " + json);
        Result4wx result = restTemplate.postForObject(createMenuUrl, menuDTO, Result4wx.class);
        logger.info("创建菜单结果：" + JSONObject.toJSONString(result));

    }

    @Deprecated
    public Result getTemplate(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            return Result.fail();
        }
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + accessToken;
        Map<String, Object> params = new HashMap<>();
        params.put("template_id_short", "ZLRkh_yfy3Yx9LbRBejAB9nZ7SYFueCNk4HvT5nVCRY");
        Result4wx result4wx = restTemplate.postForObject(url, params, Result4wx.class);
        if (result4wx.getErrcode().equals("0")) {
            return Result.success(result4wx.getTemplate_id());
        } else {
            return Result.fail();
        }
    }

    @Value("${wx.template.id}")
    private String templateId;

    public Result sendTemplate(String openId, BigDecimal money) {
        String accessToken = getWeChatAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            return Result.fail();
        }

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        String date = "" + DateUtil.formatDate(new Date(), DateUtil.FormatType.GBK_MINUTE);
        String type = "红包";
        String curAmount = "" + MoneyUtils.formatMoney(money) + "元";
        String remark = "祝您生活愉快！";
        String link = host + "/h9-weixin/#/account/personal";
        TemplateDTO templateDTO = TemplateDTO.builder()
                .touser(openId)
                .template_id(templateId)
                .url(link)
                .data(new TemplateDTO.DataBean()
                        .setFirst(new TemplateDTO.DataBean.FirstBean()
                                .setValue("到账通知"))
                        .setTradeDateTime(new TemplateDTO.DataBean.TradeDateTimeBean()
                                .setValue(date))
                        .setTradeType(new TemplateDTO.DataBean.TradeTypeBean()
                                .setValue(type))
                        .setCurAmount(new TemplateDTO.DataBean.CurAmountBean()
                                .setValue(curAmount))
                        .setRemark(new TemplateDTO.DataBean.RemarkBean()
                                .setValue(remark))

                ).build();

        String requestBody = JSONObject.toJSONString(templateDTO);
        logger.info("请求数据：" + requestBody);
        Result4wx result4wx = restTemplate.postForObject(url, templateDTO, Result4wx.class);
        logger.info("发送模块消息结果：" + JSONObject.toJSONString(result4wx));

        if (result4wx.getErrcode().equals("0")) {
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    public static void main(String[] args) {
        MenuDTO.MenuDTOBuilder builder = MenuDTO.builder();
        MenuDTO menuDTO = builder
                .button(Arrays.asList(
                        new MenuDTO.ButtonBean()
                                .setType("view")
                                .setKey("12")
                                .setUrl("https://weixin-h9.thy360.com/h9-weixin/#/active/hongbao")
                                .setName("扫瓶盖抢红包"),
                        new MenuDTO.ButtonBean()
                                .setType("view")
                                .setKey("23")
                                .setUrl("https://weixin-h9.thy360.com/h9-weixin/#/shop")
                                .setName("徽酒商城"),
                        new MenuDTO.ButtonBean()
                                .setType("view")
                                .setKey("31")
                                .setUrl("https://weixin-h9.thy360.com/h9-weixin/#/account/personal")
                                .setName("旅游健康基金"))
                ).build();

        System.out.println(JSONObject.toJSONString(menuDTO));
    }

    @Data
    @Accessors(chain = true)
    public static class Result4wx {
        private String errcode = "";
        private String errmsg = "";
        private String template_id = "";
        private String msgid= "";
        private String ticket= "";
    }


    public enum EventEnum {
        SUBSCRIBE("subscribe"),
        SCAN("SCAN");

        private String value;

        EventEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
