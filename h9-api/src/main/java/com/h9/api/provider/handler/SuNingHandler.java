package com.h9.api.provider.handler;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * SuNingHandler:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/26
 * Time: 10:17
 */
@Component
public class SuNingHandler {

    public String post(String url,MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
//  输出结果
        System.out.println(response.getBody());
        return response.getBody();
    }


}
