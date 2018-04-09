package com.h9.api.model.vo.topic;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.google.gson.JsonObject;
import lombok.*;

import java.util.*;

/**
 * Created by Ln on 2018/4/9.
 */
@Data
public class GoodsTopicDetailVO {

    private String borderImg;

    private String bgColor;

    private String titleColor;

    private String priceColor;

    private String name;

    List<Modules> modules = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Modules{
        private String img;
        private List<Map<String, String>> goodsInfo = new ArrayList<>();
    }

//    public static void main(String[] args) {
//        GoodsTopicDetailVO vo = new GoodsTopicDetailVO();
//        Modules mo = new Modules("http://12.jpg","100");
//        vo.setModules(Arrays.asList(mo));
//        String s = JSONObject.toJSONString(vo);
//        System.out.println(s);
//    }
}
