package com.h9.api.service.wxEvent.model;

import com.h9.common.utils.XMLUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 音乐消息
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wxMusic extends Message4wxText {
    private String Title;
    private String Description;
    private String MusicURL;
    private String HQMusicUrl;
    private String ThumbMediaId;

    public static void main(String[] args) {
        Message4wxMusic message4wxText = new Message4wxMusic();
        message4wxText.setToUserName("ldh").setFromUserName("ll").setCreateTime("2017");
        String s = XMLUtils.convertToXml(message4wxText);
        System.out.println(s);
    }
}
