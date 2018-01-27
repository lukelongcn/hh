package com.h9.api.provider.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * SuNingResult:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/26
 * Time: 15:01
 */

@Data
public class SuNingResult {

    private String content;
    private String sign;
    private String sign_type;
    private String vk_version;


    public SuNingContent getContent(){
        return JSONObject.parseObject(content, SuNingContent.class);
    }


}
