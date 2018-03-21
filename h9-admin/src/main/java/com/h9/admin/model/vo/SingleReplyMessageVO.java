package com.h9.admin.model.vo;

import com.h9.common.db.entity.wxEvent.ReplyMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/21
 */
@Data
public class SingleReplyMessageVO {

    @ApiModelProperty(value = "id" )
    private Long id;
    @ApiModelProperty(value = "规则名" )
    private String orderName;
    @ApiModelProperty(value = "回复内容类型" )
    private String contentType;
    @ApiModelProperty(value = "回复内容" )
    private String content;
    @ApiModelProperty(value = "使用状态 1 使用 2 禁用" )
    private Integer status;
    @ApiModelProperty(value = "排序" )
    private Integer sort;
    @ApiModelProperty(value = "规则类型" )
    private String matchStrategy;
    @ApiModelProperty(value = "关键字" )
    private String keyWord;

    public SingleReplyMessageVO(ReplyMessage replyMessage){
        BeanUtils.copyProperties(replyMessage,this);
    }
}
