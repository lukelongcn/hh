package com.h9.api.service.wxEvent.model;

import lombok.Setter;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/16
 */
@Setter
@Accessors(chain = true)
@XmlRootElement(name = "Video")
public class WXVideo {
    // 通过素材管理中的接口上传多媒体文件，得到的id
    private String MediaId;

    @XmlElement(name = "MediaId")
    public String getMediaId() {
        return MediaId;
    }
}
