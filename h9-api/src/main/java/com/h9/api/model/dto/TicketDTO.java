package com.h9.api.model.dto;

/**
 * Created by itservice on 2018/1/25.
 */
public class TicketDTO {

    /**
     * expire_seconds : 604800
     * action_name : QR_SCENE
     * action_info : {"scene":{"scene_id":123}}
     */

    private int expire_seconds;
    private String action_name;
    private ActionInfoBean action_info;

    public int getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(int expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public ActionInfoBean getAction_info() {
        return action_info;
    }

    public void setAction_info(ActionInfoBean action_info) {
        this.action_info = action_info;
    }

    public static class ActionInfoBean {
        /**
         * scene : {"scene_id":123}
         */

        private SceneBean scene;

        public SceneBean getScene() {
            return scene;
        }

        public void setScene(SceneBean scene) {
            this.scene = scene;
        }

        public static class SceneBean {
            /**
             * scene_id : 123
             */

            private int scene_id;

            private String scene_str;

            public int getScene_id() {
                return scene_id;
            }

            public void setScene_id(int scene_id) {
                this.scene_id = scene_id;
            }

            public String getScene_str() {
                return scene_str;
            }

            public void setScene_str(String scene_str) {
                this.scene_str = scene_str;
            }
        }
    }
}
