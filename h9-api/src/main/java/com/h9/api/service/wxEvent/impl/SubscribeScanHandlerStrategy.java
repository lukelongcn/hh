package com.h9.api.service.wxEvent.impl;

import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.EventService;
import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.EventHandlerStrategyFactory;
import com.h9.api.service.wxEvent.model.Message4wxText;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import com.h9.common.utils.DateUtil;
import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DateTimeException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 关注，描述事件处理
 */
@Service
public class SubscribeScanHandlerStrategy implements EventHandlerStrategy<Message4wxText> {
    @Resource
    private EventService eventService;

    @Resource
    ReplyMessageRepository replyMessageRepository;

    @Override
    public Message4wxText handler(Map map, List<ReplyMessage> replyMessageList) {

        eventService.handleSubscribeAndScan(map);
        // 取得数据库回复对象
        String orderName = ReplyMessage.orderTypeEnum.FOLLOW_REPLY.getDesc();
        ReplyMessage replyMessage = replyMessageRepository.fingByOrderName(orderName);
        return EventHandlerStrategyFactory.getXml(map,replyMessage);

    }

}
