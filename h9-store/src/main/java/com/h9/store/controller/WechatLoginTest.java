package com.h9.store.controller;


import com.h9.common.base.Result;
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
import java.util.Map;

/**
 * Created by itservice on 2017/12/11.
 */

@RestController
public class WechatLoginTest {

    private Logger logger = Logger.getLogger(this.getClass());

    @GetMapping("/test/wx/login")
    public void wxLoginTest(HttpServletResponse response) throws IOException {

        String appId = "wx88536d7560a36bc1";
        String redirectUrl = "http://api-test-h9.thy360.com/h9/store/wxlogin";
        String appsecret = "c7859a462234521b1c9fb6ad66585e36";
        String scope = "snsapi_userinfo";

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s" +
                "&redirect_uri=%s&response_type=code&scope=%s";
        url = String.format(url, appId, redirectUrl, scope);
        response.sendRedirect(url);
    }

    @GetMapping("/wxlogin")
    public void code(@RequestParam String code, @RequestParam String state) {
        logger.info("code : " + code);
    }
}
