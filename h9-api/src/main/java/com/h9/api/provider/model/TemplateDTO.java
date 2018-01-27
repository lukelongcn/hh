package com.h9.api.provider.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/1/26.
 */
@Data
@Builder
public class TemplateDTO {


    /**
     * touser : oXW4Mw2JMAlYYrH9R6X2VLbqFAGQ
     * template_id : ZLRkh_yfy3Yx9LbRBejAB9nZ7SYFueCNk4HvT5nVCRY
     * url : http://weixin.qq.com/download
     * data : {"first":{"value":"到账通知","color":"#173177"},"tradeDateTime":{"value":"交易时间：2013年9月23日","color":"#173177"},"tradeType":{"value":"交易类型：红包","color":"#173177"},"curAmount":{"value":"交易金额：60元","color":"#173177"},"remark":{"value":"祝您生活愉快！","color":"#173177"}}
     */

    private String touser;
    private String template_id;
    private String url;
    private DataBean data;


    @Data
    @Accessors(chain = true)
    public static class DataBean {
        /**
         * first : {"value":"到账通知","color":"#173177"}
         * tradeDateTime : {"value":"交易时间：2013年9月23日","color":"#173177"}
         * tradeType : {"value":"交易类型：红包","color":"#173177"}
         * curAmount : {"value":"交易金额：60元","color":"#173177"}
         * remark : {"value":"祝您生活愉快！","color":"#173177"}
         */

        private FirstBean first;
        private TradeDateTimeBean tradeDateTime;
        private TradeTypeBean tradeType;
        private CurAmountBean curAmount;
        private RemarkBean remark;



        @Data
        @Accessors(chain = true)
        public static class FirstBean {
            /**
             * value : 到账通知
             * color : #173177
             */

            private String value;
            private String color = "#173177";


        }
        @Data
        @Accessors(chain = true)
        public static class TradeDateTimeBean {
            /**
             * value : 交易时间：2013年9月23日
             * color : #173177
             */
            private String value;
            private String color = "#173177";

        }
        @Data
        @Accessors(chain = true)
        public static class TradeTypeBean {
            /**
             * value : 交易类型：红包
             * color : #173177
             */

            private String value;
            private String color = "#173177";


        }
        @Data
        @Accessors(chain = true)
        public static class CurAmountBean {
            /**
             * value : 交易金额：60元
             * color : #173177
             */

            private String value;
            private String color = "#173177";


        }
        @Data
        @Accessors(chain = true)
        public static class RemarkBean {
            /**
             * value : 祝您生活愉快！
             * color : #173177
             */

            private String value;
            private String color = "#173177";


        }
    }
}
