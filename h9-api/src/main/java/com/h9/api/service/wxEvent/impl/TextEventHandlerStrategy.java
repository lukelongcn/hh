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


    /**
     * 处理关键字回复和自动回复,先判断关键字，再自动回复
     * @param map httpRequest中的请求参数转换成map
     * @return
     */
    @Override
    public Message4wxText handler(Map map) {
        //TODO 处理关键字回复和自动回复
        return null;
    }
}
