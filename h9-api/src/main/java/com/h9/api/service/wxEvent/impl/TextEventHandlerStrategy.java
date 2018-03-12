package com.h9.api.service.wxEvent.impl;

import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.model.Message4wx;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 文本消息处理
 */
@Component
public class TextEventHandlerStrategy implements EventHandlerStrategy<Message4wx> {

    @Resource
    private ReplyMessageRepository replyMessageRepository;

    /**
     * 处理关键字回复和自动回复,先判断关键字，再自动回复
     * @param map httpRequest中的请求参数转换成map
     * @return
     */
    @Override
    public Message4wx handler(Map map, List<ReplyMessage> replyMessageList) {

        String event = map.get("MsgType").toString();
        if (event.equals(WeChatProvider.EventEnum.SUBSCRIBE.getValue())){

        }
        return null;
    }
}
