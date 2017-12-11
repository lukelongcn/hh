package com.h9.store.controller;


import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.Result;
import org.apache.commons.io.Charsets;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2017/12/11.
 */

@RestController
public class WechatLoginTest {

    private Logger logger = Logger.getLogger(this.getClass());
    String appId = "wx88536d7560a36bc1";
    String appsecret = "c7859a462234521b1c9fb6ad66585e36";
    String scope = "snsapi_userinfo";


    @GetMapping("/test/wx/login")
    public void wxLoginTest(HttpServletResponse response) throws IOException {

        //https://weixin-test-h9.thy360.com
        String redirectUrl = "https://weixin-test-h9.thy360.com/h9/store/wxlogin";

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s" +
                "&redirect_uri=%s&response_type=code&scope=%s";
        url = String.format(url, appId, redirectUrl, scope);
        response.sendRedirect(url);
    }

    @GetMapping("/wxlogin")
    public String code(@RequestParam String code, @RequestParam String state) {
        logger.info("code : " + code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, appId, appsecret, code);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        String res = restTemplate.getForObject(url, String.class);
        logger.info("res : " + res);
        acResponse acResponse = JSONObject.parseObject(res, acResponse.class);
        String access_token = acResponse.getAccess_token();

        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
        getUserInfoUrl = String.format(getUserInfoUrl, access_token, acResponse.getOpenid());
        logger.info("getUserInfoUrl : "+getUserInfoUrl);
        String userInfo = restTemplate.getForObject(getUserInfoUrl, String.class);

        logger.info("userInfo : "+userInfo);

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body>");
        sb.append("<h1>");
        sb.append(new String(userInfo.toString().getBytes(Charsets.ISO_8859_1),Charsets.UTF_8));
        sb.append("</h1>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "{\"openid\":\"omOSC1FmOkoCuQW75k14r8ZadQ8c\",\"nickname\":\"ä¹°å¥¶ç³�ç��å¤§å�¥å�¥î��\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"å®�é��å°�\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmopen\\/vi_32\\/Q0j4TwGTfTLC1MgMcjlPc6BJcEA5zYCkFcJiaGCXY7w80dHyzKjOHycLiaeEN2NvRhHiasTFJQJnP3DNZJWicNnzXA\\/0\",\"privilege\":[]}";
        byte[] bytes = s.getBytes(Charsets.ISO_8859_1);
        String s2 = new String(bytes,Charsets.UTF_8);
        System.out.println(s2);
    }
    private static class acResponse {
        private String access_token;
        private String expires_in;

        private String refresh_token;
        private String openid;
        private String scope;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }
}
