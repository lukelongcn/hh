package com.h9.api.service.wxEvent;


import com.h9.api.service.wxEvent.impl.MediaEventHandlerStrategy;
import com.h9.api.service.wxEvent.impl.SubscribeScanHandlerStrategy;
import com.h9.api.service.wxEvent.impl.TextEventHandlerStrategy;
import org.jboss.logging.Logger;

import java.util.Map;

import static com.h9.api.provider.WeChatProvider.EventEnum.TEXT;


/**
 * 策略工厂
 */
public class EventHandlerStrategyFactory {

    private Logger logger = Logger.getLogger(this.getClass());
    private static EventHandlerStrategyFactory eventHandlerStrategyFactory = null;

    private static Map<String,EventHandlerStrategy> strategyMap;
    static {
        strategyMap.put(TEXT.getValue(), new TextEventHandlerStrategy());
        strategyMap.put(TEXT.getValue(), new SubscribeScanHandlerStrategy());
        strategyMap.put(TEXT.getValue(), new MediaEventHandlerStrategy());
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
        EventHandlerStrategy eventHandlerStrategy = strategyMap.get(key);
        if(eventHandlerStrategy == null){
            logger.error("所匹配到的策略为空,key : "+key);
        }
        return eventHandlerStrategy;
    }
}
