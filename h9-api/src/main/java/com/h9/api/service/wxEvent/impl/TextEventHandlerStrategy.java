package com.h9.api.service.wxEvent.impl;

import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.model.Message4wxText;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文本消息处理
 */
@Component
public class TextEventHandlerStrategy implements EventHandlerStrategy<Message4wxText> {


    @Override
    public Message4wxText handler(Map map) {

        return null;
    }
}
