package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.model.Message4wx;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 小视频消息处理
 */
@Service
public class ShortVideoEventHandlerStrategy implements EventHandlerStrategy<Message4wx> {
    @Override
    public Message4wx handler(Map map, List<ReplyMessage> replyMessageList) {
        return null;
    }
}
