package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/2/26.
 */
@Data
@Accessors(chain = true)
public class Message4wxMusic extends Message4wxText {
    private String Title;
    private String Description;
    private String MusicURL;
    private String HQMusicUrl;
    private String ThumbMediaId;
}
