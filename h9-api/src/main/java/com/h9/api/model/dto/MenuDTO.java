package com.h9.api.model.dto;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class MenuDTO {

    private List<ButtonBean> button;

    public List<ButtonBean> getButton() {
        return button;
    }

    public void setButton(List<ButtonBean> button) {
        this.button = button;
    }

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class ButtonBean {
        /**
         * type : click
         * name : 扫瓶盖抢红包
         * key : V1001_TODAY_MUSIC
         * sub_button : [{"type":"view","name":"旅游健康基金","url":"http://www.soso.com/"},{"type":"click","name":"赞一下我们","key":"V1001_GOOD"}]
         */

        private String type;
        private String name;
        private String key;
        private String url;
        private List<SubButtonBean> sub_button;


        @Setter
        @Getter
        @Accessors(chain = true)
        public static class SubButtonBean {
            /**
             * type : view
             * name : 旅游健康基金
             * url : http://www.soso.com/
             * key : V1001_GOOD
             */

            private String type;
            private String name;
            private String url;
            private String key;


        }
    }

    public static void main(String[] args) {
        MenuDTO MenuDTO = new MenuDTOBuilder()
                .button(Arrays.asList(
                        new ButtonBean()
                                .setType("clink")
                                .setName("扫瓶盖抢红包"),
                        new ButtonBean()
                                .setType("clink")
                                .setName("徽酒商城"),
                        new ButtonBean()
                                .setType("clink")
                                .setName("旅游健康基金"))
                ).build();
        String s = JSONObject.toJSONString(MenuDTO);
        System.out.println(s);
    }
}
