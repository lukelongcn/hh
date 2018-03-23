package com.h9.common.db.entity.wxEvent;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import static java.util.Arrays.stream;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用于储存 回复微信消息 内容
 */
@Table(name = "reply_message")
@Entity
@Data
public class ReplyMessage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "order_name",columnDefinition = "varchar(200) COMMENT '规则名'")
    private String orderName;

    @Column(name = "content_type",columnDefinition = "varchar(200) COMMENT '回复内容类型'")
    private String contentType;

    @Column(name = "content",columnDefinition = "text COMMENT '回复内容或素材id'")
    private String content;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 启用， 2 禁用 '")
    private Integer status = 1;

    @Column(name = "sort",nullable = false,columnDefinition = "tinyint default 1 COMMENT '排序 '")
    private Integer sort = 1;
    /**
     * 匹配策略
     * @see matchStrategyEnum
     */
    @Column(name = "match_strategy",columnDefinition = "tinyint default 1 COMMENT '匹配策略 1 为完全匹配 ，2为半匹配 ，3正则匹配 ," +
            " 4,自动回复 , 5,关注回复'")
    private Integer matchStrategy;

    @Column(name = "key_word",columnDefinition = "varchar(200) COMMENT '匹配关键词'")
    private String keyWord;

    @Column(name = "event_type",columnDefinition = "varchar(200) COMMENT '匹配关键词'")
    private String eventType;

    public enum matchStrategyEnum{
        ALL_MATCH(1,"完全匹配"),
        SECTION_MATCH(2,"半匹配"),
        REGEX_MATCH(3, "正则匹配"),
        AUTOMATIC_REPLY(4,"自动回复"),
        FOLLOW_REPLY(5,"关注回复");
        private int code;
        private String desc;

        matchStrategyEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByCode(Integer matchStrategy) {
            ReplyMessage.matchStrategyEnum matchStrategyEnum = stream(values()).filter(o -> o.getCode()==matchStrategy).limit(1).findAny().orElse(null);
            return matchStrategyEnum==null?null:matchStrategyEnum.getDesc();
        }

        public static Integer getCodeByDesc(String desc) {
            ReplyMessage.matchStrategyEnum matchStrategyEnum = stream(values()).filter(o -> o.getDesc()==desc).limit(1).findAny().orElse(null);
            return matchStrategyEnum==null?null:matchStrategyEnum.getCode();
        }

    }

    public enum  orderTypeEnum{
        LUCKY_DRAW(1,"抽奖活动"),
        GOODS(2,"商品"),
        KEYWORD_MATCHING(3,"关键字匹配"),
        AUTOMATIC_REPLY(4,"自动回复"),
        FOLLOW_REPLY(5,"关注回复"),
        REGULAR_MATCHING(6,"正则匹配");

        private int code;
        private String desc;
        orderTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByCode(int code){
            ReplyMessage.orderTypeEnum orderTypeEnum = stream(values()).filter(o -> o.getCode()==code).limit(1).findAny().orElse(null);
            return orderTypeEnum==null?null:orderTypeEnum.getDesc();
        }
    }
    public enum  contentTypeEnum{
        TEXT("text","文本"),
        VOICE("voice","语音"),
        IMAGE("image","图片"),
        VIDEO("video","视频");

        private String code;
        private String desc;
        contentTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByCode(String code){
            ReplyMessage.contentTypeEnum contentTypeEnum = stream(values()).filter(o -> o.getCode().equals(code)).limit(1).findAny().orElse(null);
            return contentTypeEnum==null?null:contentTypeEnum.getDesc();
        }
    }
    public static void main(String[] args) {
        System.out.printf(ReplyMessage.contentTypeEnum.getDescByCode("video"));
    }

}
