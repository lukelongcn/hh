package com.h9.store.controller;


import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itservice on 2017/12/11.
 */
@RestController
public class WechatLoginTest {

    @GetMapping("/test/wx/login")
    public Result wxLoginTest(){
        String appId = "wx88536d7560a36bc1";
        String redirectUrl = "";
        String appsecret = "c7859a462234521b1c9fb6ad66585e36";

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID" +
                "&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE";
        return null;
    }
}
