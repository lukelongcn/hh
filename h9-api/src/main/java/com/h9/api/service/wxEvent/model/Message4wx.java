package com.h9.api.service.wxEvent.model;

import com.h9.api.provider.handler.CDataContentHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文本消息
 */
@Data
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
        message4Wx.setToUserName("<kk").setFromUserName("<ll").setCreateTime(2017);
        String s = CDataContentHandler.ojbectToXmlWithCDATA(Message4wx.class, message4Wx);
        System.out.println(s);
    }
}
