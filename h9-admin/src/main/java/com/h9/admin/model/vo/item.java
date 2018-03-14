package com.h9.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/14
 */
@ApiModel("微信素材item")
public class item {

        @ApiModelProperty("MEDIA_ID")
        private long media_id;

        @ApiModelProperty("这篇图文消息素材的最后更新时间")
        private Date update_time;

        @ApiModelProperty("文件名称")
        private String name;

        @ApiModelProperty("图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL")
        private String url;

       public item(){
                this.media_id = media_id;
                this.name = name;
                this.update_time = update_time;
                this.url = url;
        }

}
