package com.h9.admin.model.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * <p>Title:${file.className}</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @date ${date}
 */
@Data
@ApiModel("微信素材信息")
public class WXMatterDTO {


    @ApiModelProperty("素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）")
    private String type;
    @ApiModelProperty("从全部素材的该偏移位置开始返回，0表示从第一个素材 返回")
    private Integer offset = 0;
    @ApiModelProperty("返回素材的数量，取值在1到20之间")
    private Integer count = 20;

}
