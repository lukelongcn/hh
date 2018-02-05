package com.h9.api;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test2 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String decode = URLEncoder.encode("https://weixin-dev-h9.thy360.com/h9-weixin/#/account/hongbao/result?id=1", "UTF-8");
        System.out.println(decode);

    }
}
