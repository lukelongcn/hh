package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.EventHandlerStrategyFactory;
import com.h9.api.service.wxEvent.model.Message4wxText;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 关注，描述事件处理
 */
@Service
public class SubscribeScanHandlerStrategy implements EventHandlerStrategy<Message4wxText> {


    @Resource
    ReplyMessageRepository replyMessageRepository;

    @Transactional
    @Override
    public Message4wxText handler(Map map, List<ReplyMessage> replyMessageList) {
        // 取得数据库回复对象
        String orderName = ReplyMessage.orderTypeEnum.FOLLOW_REPLY.getDesc();
        ReplyMessage replyMessage = replyMessageRepository.fingByOrderName(orderName);
        return EventHandlerStrategyFactory.getXml(map,replyMessage);

    }

}
