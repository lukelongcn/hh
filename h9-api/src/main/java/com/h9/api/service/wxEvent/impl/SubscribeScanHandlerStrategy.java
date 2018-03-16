package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.EventHandlerStrategyFactory;
import com.h9.api.service.wxEvent.model.Message4wx;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static com.h9.api.provider.WeChatProvider.EventEnum.TEXT;
import static com.h9.common.db.entity.wxEvent.ReplyMessage.matchStrategyEnum.FOLLOW_REPLY;

/**
 * 关注，描述事件处理
 */
@Service
public class SubscribeScanHandlerStrategy implements EventHandlerStrategy<Message4wx> {


    @Resource
    ReplyMessageRepository replyMessageRepository;

    @Transactional
    @Override
    public Message4wx handler(Map map, List<ReplyMessage> replyMessageList) {
        // 取得数据库回复对象
        String orderName = FOLLOW_REPLY.getDesc();
        ReplyMessage replyMessage = replyMessageRepository.fingByOrderName(orderName);
        if (replyMessage == null){
            return null ;
        }
        Message4wx message4wx = EventHandlerStrategyFactory.getXml(map,replyMessage);
        return message4wx;
    }

}
