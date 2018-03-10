package com.h9.api.service.wxEvent;


import com.h9.api.service.wxEvent.impl.*;
import io.swagger.annotations.Scope;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static com.h9.api.provider.WeChatProvider.EventEnum.*;


/**
 * 策略工厂
 */
@Component
public class EventHandlerStrategyFactory {

    private Logger logger = Logger.getLogger(this.getClass());
    private boolean init = false;
    private static Map<String,EventHandlerStrategy> strategyMap = null;

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
}
