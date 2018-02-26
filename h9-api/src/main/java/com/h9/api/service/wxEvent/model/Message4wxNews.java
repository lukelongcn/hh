package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2018/2/26.
 */
@Data
@Accessors(chain = true)
public class Message4wxNews extends Message4wxText {
    private String ArticleCount ;
    private String Articles;
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
}
