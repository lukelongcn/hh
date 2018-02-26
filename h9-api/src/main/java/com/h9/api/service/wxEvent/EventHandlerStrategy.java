package com.h9.api.service.wxEvent;


import java.util.Map;

/**
 * 微信事件处理接口
 */
public interface EventHandlerStrategy<T> {

     /**
      * 处理方法
      * @param map httpRequest中的请求参数转换成map
      */
     T handler(Map map );
}
