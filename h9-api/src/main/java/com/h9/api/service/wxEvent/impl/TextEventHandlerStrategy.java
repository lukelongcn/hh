package com.h9.api.service.wxEvent.impl;

import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.EventHandlerStrategyFactory;
import com.h9.api.service.wxEvent.model.Message4wx;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.db.repo.ReplyMessageRepository;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.form.TextareaTag;

import javax.annotation.Resource;
import javax.xml.soap.Text;
import java.util.List;
import java.util.Map;

import static com.h9.api.provider.WeChatProvider.EventEnum.*;

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
        String key = map.get("Content").toString();
        if (key != null){
            // 全匹配关键字回复
            ReplyMessage replyMessage = replyMessageRepository.fingByAllKey(key);
            // 半匹配关键字回复
            if (replyMessage == null){
               replyMessage = replyMessageRepository.fingByHalfKey("%"+key+"%");
            }
            // 正则匹配
            else if (replyMessage == null){
                replyMessage = replyMessageRepository.fingByRegexp(key);
            }
            // 自动回复
            else if (replyMessage == null){
                replyMessage = replyMessageRepository.findOneKey();
            }
            return getMessage4wx(map,replyMessage);
        }
        // 自动回复
        ReplyMessage replyMessage = replyMessageRepository.findOneKey();
        return getMessage4wx(map,replyMessage);
    }

    private Message4wx getMessage4wx(Map map,ReplyMessage replyMessage){
        Message4wx message4wx = EventHandlerStrategyFactory.getXml(map,replyMessage);
        // 回复文本
        if (replyMessage.getContentType().equals(TEXT.getValue())){
            message4wx.setContent(replyMessage.getContent());
        }
        // 回复语音或图片
        else if (replyMessage.getContentType().equals(VOICE.getValue())||
                replyMessage.getContentType().equals(IMAGE.getValue())){
            message4wx.setMediaId(replyMessage.getContent());
        }
        // 回复视频
        else if (replyMessage.getContentType().equals(VIDEO.getValue())){
            message4wx.setMediaId(replyMessage.getContent());
            message4wx.setTitle(replyMessage.getTitle());
            message4wx.setDescription(replyMessage.getDescription());
        }
        return message4wx;
    }
}
