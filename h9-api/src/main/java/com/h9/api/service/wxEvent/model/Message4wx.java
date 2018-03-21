package com.h9.api.service.wxEvent.model;

import com.h9.api.provider.handler.CDataContentHandler;
import com.h9.common.db.entity.config.Image;
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

    // 通过素材管理中的接口上传多媒体文件，得到的id
    private String MediaId;

    private WXVoice Voice;

    private WXImage Image;

    private WXVideo Video;


    public static void main(String[] args) throws Exception{
        Message4wx message4Wx = new Message4wx();
        message4Wx.setToUserName("kk");
        message4Wx.setFromUserName("ll");
        message4Wx.setCreateTime(2017);

        WXImage wxImage = new WXImage();
        wxImage.setMediaId("asdfsdfsdfsdf");
        message4Wx.setImage(wxImage);
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

    @XmlElement(name = "MediaId")
    public String getMediaId() {
        return MediaId;
    }
    @XmlElement(name = "Image")
    public WXImage getImage() {
        return Image;
    }
    @XmlElement(name = "Voice")
    public WXVoice getVoice() {
        return Voice;
    }
    @XmlElement(name = "Video")
    public WXVideo getVideo() {
        return Video;
    }
}
