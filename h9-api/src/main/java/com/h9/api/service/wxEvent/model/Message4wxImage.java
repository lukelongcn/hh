package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图片消息
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wxImage extends Message4wxText {
    private String MediaId;
}
