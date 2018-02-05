package com.h9.api;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test2 {

    public static void main(String[] args) {
        String url = "https://weixin-dev-h9.thy360.com/h9-weixin/#/account/hongbao/result?id=bdf36da128284557bf9dc8f79e1ba722";
        try {
            String encode = URLEncoder.encode(url, "UTF-8");
            System.out.println(encode);

            String decode = URLDecoder.decode(encode, "UTF-8");
            System.out.println(decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
