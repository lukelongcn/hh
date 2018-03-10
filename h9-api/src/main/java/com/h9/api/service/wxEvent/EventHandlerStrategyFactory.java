package com.h9.api.service.wxEvent;


import com.h9.api.service.wxEvent.impl.*;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.h9.api.provider.WeChatProvider.EventEnum.*;


/**
 * 策略工厂
 */
public class EventHandlerStrategyFactory {

    private Logger logger = Logger.getLogger(this.getClass());
    private static EventHandlerStrategyFactory eventHandlerStrategyFactory = null;

    private static Map<String,EventHandlerStrategy> strategyMap = new HashMap<>();
    static {
        strategyMap.put(TEXT.getValue(), new TextEventHandlerStrategy());
        strategyMap.put(SCAN.getValue(), new SubscribeScanHandlerStrategy());
        strategyMap.put(SUBSCRIBE.getValue(), new SubscribeScanHandlerStrategy());
        strategyMap.put(IMAGE.getValue(), new ImageEventHandlerStrategy());
        strategyMap.put(VOICE.getValue(), new VoiceEventHandlerStrategy());
        strategyMap.put(VIDEO.getValue(), new VideoEventHandlerStrategy());
        strategyMap.put(SHORTVIDEO.getValue(), new ShortVideoEventHandlerStrategy());
        strategyMap.put(LOCATION.getValue(), new LocationEventHandlerStrategy());
        strategyMap.put(LINK.getValue(), new LinkEventHandlerStrategy());
    }
    private EventHandlerStrategyFactory(){}

    public static EventHandlerStrategyFactory getInstance(){
        if(eventHandlerStrategyFactory == null){
            eventHandlerStrategyFactory = new EventHandlerStrategyFactory();
        }
        return eventHandlerStrategyFactory;
    }

    /**
     * 根据key 匹配 处理策略
     * @param key
     * @return
     */
    public EventHandlerStrategy getStrategy(String key){
        if(key == null)return null;
        EventHandlerStrategy eventHandlerStrategy = strategyMap.get(key);
        if(eventHandlerStrategy == null){
            logger.error("所匹配到的策略为空,key : "+key);
        }
        return eventHandlerStrategy;
    }
}
