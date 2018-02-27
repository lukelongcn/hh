package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 视频消息.
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wxVideo extends Message4wxText {
    private String MediaId;
    private String Title;
    private String Description;
}
