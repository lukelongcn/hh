package com.h9.api.service.wxEvent;


import com.h9.api.service.wxEvent.impl.*;
import com.h9.api.service.wxEvent.model.Message4wx;
import com.h9.api.service.wxEvent.model.WXImage;
import com.h9.common.db.entity.wxEvent.ReplyMessage;
import com.h9.common.utils.DateUtil;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.h9.api.provider.WeChatProvider.EventEnum.*;


/**
 * 策略工厂
 */
@Component
public class EventHandlerStrategyFactory {

    private Logger logger = Logger.getLogger(this.getClass());
    private boolean init = false;
    private static Map<String, EventHandlerStrategy> strategyMap = new HashMap<>();

    @Resource
    private TextEventHandlerStrategy textEventHandlerStrategy;
    @Resource
    private SubscribeScanHandlerStrategy subscribeScanHandlerStrategy;
    @Resource
    private ImageEventHandlerStrategy imageEventHandlerStrategy;
    @Resource
    private VoiceEventHandlerStrategy voiceEventHandlerStrategy;
    @Resource
    private VideoEventHandlerStrategy videoEventHandlerStrategy;
    @Resource
    private ShortVideoEventHandlerStrategy shortVideoEventHandlerStrategy;
    @Resource
    private LocationEventHandlerStrategy locationEventHandlerStrategy;
    @Resource
    private LinkEventHandlerStrategy linkEventHandlerStrategy;


    public void init() {
        strategyMap.put(TEXT.getValue(), textEventHandlerStrategy);
        strategyMap.put(SCAN.getValue(), subscribeScanHandlerStrategy);
        strategyMap.put(SUBSCRIBE.getValue(), subscribeScanHandlerStrategy);
        strategyMap.put(IMAGE.getValue(), imageEventHandlerStrategy);
        strategyMap.put(VOICE.getValue(), voiceEventHandlerStrategy);
        strategyMap.put(VIDEO.getValue(), videoEventHandlerStrategy);
        strategyMap.put(SHORTVIDEO.getValue(), shortVideoEventHandlerStrategy);
        strategyMap.put(LOCATION.getValue(), locationEventHandlerStrategy);
        strategyMap.put(LINK.getValue(), linkEventHandlerStrategy);
        init = true;
        logger.info("初始化map完成");
    }
    private EventHandlerStrategyFactory(){}


    /**
     * 根据key 匹配 处理策略
     * @param key
     * @return
     */
    public EventHandlerStrategy getStrategy(String key){
        if(key == null)return null;
        if(!init){
            init();
        }
        EventHandlerStrategy eventHandlerStrategy = strategyMap.get(key);
        if(eventHandlerStrategy == null){
            logger.error("所匹配到的策略为空,key : "+key);
        }
        return eventHandlerStrategy;
    }

    public static Message4wx getXml(Map map, ReplyMessage replyMessage){
        // 返回xml
        Message4wx message4Wx = new Message4wx();
        String dateS = DateUtil.formatDate(new Date(), DateUtil.FormatType.NON_SEPARATOR_DAY);
        message4Wx
                .setToUserName(map.get("FromUserName").toString())
                .setFromUserName(map.get("ToUserName").toString())
                .setCreateTime(Integer.valueOf(dateS));
        System.out.println(replyMessage.getContentType());
        System.out.println(TEXT.getValue());
        // 回复文本
        if (replyMessage.getContentType().equals(TEXT.getValue())){
            message4Wx.setContent(replyMessage.getContent());
            message4Wx.setMsgType(TEXT.getValue());
        }
        // 回复图片
        else if (replyMessage.getContentType().equals(IMAGE.getValue())){
            message4Wx.setMsgType(IMAGE.getValue());
            WXImage wxImage = new WXImage().setMediaId(replyMessage.getContent());
            message4Wx.setImage(wxImage);
        }
        // 回复语音
        else if (replyMessage.getContentType().equals(VOICE.getValue())){
            message4Wx.setMsgType(VOICE.getValue());

        }
        // 回复视频
        else if (replyMessage.getContentType().equals(VIDEO.getValue())){
            message4Wx.setMediaId(replyMessage.getContent());
            message4Wx.setMsgType(VIDEO.getValue());
        }
        return message4Wx;
    }
}
