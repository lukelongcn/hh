package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图文消息
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wxNews extends Message4wxText {
    private String ArticleCount ;
    private String Articles;
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
}
