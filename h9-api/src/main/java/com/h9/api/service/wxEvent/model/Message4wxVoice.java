package com.h9.api.service.wxEvent.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by itservice on 2018/2/26.
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wxVoice extends Message4wxText {
    private String MediaId;
}
