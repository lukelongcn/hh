package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/2/26.
 */
@Data
@Accessors(chain = true)
public class Message4wxText {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MediaId;
}
