package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.EventHandlerStrategyFactory;
import com.h9.api.service.wxEvent.model.Message4wx;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 图片消息处理
 */
@Service
public class ImageEventHandlerStrategy implements EventHandlerStrategy<Message4wx> {
    @Resource
    ReplyMessageRepository replyMessageRepository;

    @Transactional
    @Override
    public Message4wx handler(Map map, List<ReplyMessage> replyMessageList) {
        // 自动回复
        ReplyMessage replyMessage = replyMessageRepository.findOneKey();
        return EventHandlerStrategyFactory.getXml(map,replyMessage);
    }
}
