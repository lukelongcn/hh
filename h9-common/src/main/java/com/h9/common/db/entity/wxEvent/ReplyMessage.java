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

    private String eventType;

    private String replyContentType;

    private String replyContent;
}
