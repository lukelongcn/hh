package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.model.Message4wxText;
import com.h9.common.db.entity.wxEvent.ReplyMessage;

import java.util.List;
import java.util.Map;

/**
 * 小视频消息处理
 */
public class ShortVideoEventHandlerStrategy implements EventHandlerStrategy<Message4wxText> {
    @Override
    public Message4wxText handler(Map map, List<ReplyMessage> replyMessageList) {
        return null;
    }
}
