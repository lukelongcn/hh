package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.EventService;
import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.model.Message4wxText;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 关注，描述事件处理
 */
@Service
public class SubscribeScanHandlerStrategy implements EventHandlerStrategy<Message4wxText> {
    @Resource
    private EventService eventService;

    @Override
    public Message4wxText handler(Map map, List<ReplyMessage> replyMessageList) {

        eventService.handleSubscribeAndScan(map);
        return null;
    }

}
