package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.model.Message4wxText;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 位置消息处理
 */
@Service
public class LocationEventHandlerStrategy implements EventHandlerStrategy<Message4wxText> {
    @Override
    public Message4wxText handler(Map map, List<ReplyMessage> replyMessageList) {
        return null;
    }
}
