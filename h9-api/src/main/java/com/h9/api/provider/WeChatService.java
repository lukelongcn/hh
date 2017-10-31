package com.h9.api.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:微信相关信息获取
 * WeChatService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:32
 */
@Service
public class WeChatService {

    @Value("${wechat.js.appid}")
    private String jsAppId;







}
