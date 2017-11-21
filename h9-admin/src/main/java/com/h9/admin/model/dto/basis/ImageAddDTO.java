package com.h9.admin.model.dto.basis;

import com.h9.common.db.entity.Image;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

/**
 * @author: George
 * @date: 2017/11/21 19:16
 */
public class ImageAddDTO {

    @ApiModelProperty(value = "标题",required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "目录",required = true)
    @NotBlank(message = "目录不能为空")
    private String directory;

    @ApiModelProperty(value = "图片url",required = true)
    @NotBlank(message = "图片url不能为空")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image toImage(){
        Image image = new Image();
        BeanUtils.copyProperties(this,image);
        return image;
    }
}
