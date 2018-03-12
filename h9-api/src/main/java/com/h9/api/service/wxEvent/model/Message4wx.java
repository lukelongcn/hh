package com.h9.api.service.wxEvent.model;

import com.h9.api.provider.handler.CDataContentHandler;
import com.h9.common.utils.XMLUtils;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文本消息
 */
@Setter
@Accessors(chain = true)
@XmlRootElement(name = "xml")
public class Message4wx {
    private String ToUserName;
    private String FromUserName;
    private int CreateTime;
    private String MsgType;
    // 文本消息
    private String Content;
    // 视频消息的标题
    private String Title;
    // 视频消息的描述
    private String Description;
    // 通过素材管理中的接口上传多媒体文件，得到的id
    private Long MediaId;


    public static void main(String[] args) throws Exception{
        Message4wx message4Wx = new Message4wx();
        message4Wx.setToUserName("kk");
        message4Wx.setFromUserName("ll");
        message4Wx.setCreateTime(2017);
        message4Wx.setMediaId(121323L);
        String s = CDataContentHandler.ojbectToXmlWithCDATA(Message4wx.class,message4Wx);
        System.out.println(s);

    }

    @XmlElement(name = "ToUserName")
    public String getToUserName() {
        return ToUserName;
    }

    @XmlElement(name = "FromUserName")
    public String getFromUserName() {
        return FromUserName;
    }

    @XmlElement(name = "CreateTime")
    public int getCreateTime() {
        return CreateTime;
    }

    @XmlElement(name = "MsgType")
    public String getMsgType() {
        return MsgType;
    }

    @XmlElement(name = "Content")
    public String getContent() {
        return Content;
    }

    @XmlElement(name = "Title")
    public String getTitle() {
        return Title;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return Description;
    }

    @XmlElement(name = "MediaId")
    public Long getMediaId() {
        return MediaId;
    }
}
