package com.h9.common.db.entity.wxEvent;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用于储存 回复微信消息 内容
 */
@Table(name = "reply_message")
@Entity
public class ReplyMessage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "event_type",columnDefinition = "varchar(200) COMMENT '事件类型'")
    private String eventType;

    @Column(name = "content_type",columnDefinition = "varchar(200) COMMENT '回复内容类型'")
    private String contentType;

    @Column(name = "content",columnDefinition = "text COMMENT '回复内容'")
    private String content;

    @Column(name = "match_regex",columnDefinition = "varchar(200) COMMENT '匹配正则'")
    private String matchRegex;


    /**
     * 匹配策略
     * @see matchStrategyEnum
     */
    @Column(name = "match_strategy",columnDefinition = "varchar(200) COMMENT '匹配策略 1 为完全匹配 ，2为半匹配 ，3正则匹配'")
    private String matchStrategy;

    @Column(name = "key_word",columnDefinition = "varchar(200) COMMENT '匹配关键词'")
    private String keyWord;

    public enum matchStrategyEnum{
        ALL_MATCH(1,"完全匹配"),
        SECTION_MATCH(2,"半匹配"),
        REGEX_MATCH(3, "正则匹配");
        private int code;
        private String desc;

        matchStrategyEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    public static void main(String[] args) {
        String s = matchStrategyEnum.ALL_MATCH.toString();
        System.out.println(s);
    }
}
