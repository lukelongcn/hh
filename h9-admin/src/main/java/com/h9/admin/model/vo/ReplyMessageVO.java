package com.h9.admin.model.vo;

import com.h9.common.db.entity.wxEvent.ReplyMessage;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * <p>Title:$file.className</p>
 * <p>Desription</p>
 *
 * @author LiYuan
 * @date $date
 */
@Data
public class ReplyMessageVO {
    private Long id;

    private String orderName;

    private String eventType;

    private String contentType;

    private String content;

    private String matchRegex;

    private Integer status;

    private Integer sort;

    private String matchStrategy;

    private String keyWord;

    public ReplyMessageVO(ReplyMessage replyMessage){
        BeanUtils.copyProperties(replyMessage,this);
    }

}
