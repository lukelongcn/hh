package com.h9.api.service.wxEvent.model;

import com.h9.common.utils.XMLUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文本消息
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wxText {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MediaId;

    public static void main(String[] args) {
        Message4wxText message4wxText = new Message4wxText();
        message4wxText.setToUserName("ldh").setFromUserName("ll").setCreateTime("2017");
        String s = XMLUtils.convertToXml(message4wxText);
        System.out.println(s);
    }
}
