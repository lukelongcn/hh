package com.h9.admin.model.dto.stick;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickTypeDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 17:29
 */
public class StickTypeDTO {

    private Long id;
    @NotBlank(message = "请填写分类名称")
    @Length(min = 2,max = 64,message = "分类名称填写过长或过短")
    private String name;
    @NotBlank(message = "请填写分类描述")
    @Length(min = 2,max = 64,message = "分类描述填写过长或过短")
    private String content;
    @NotBlank(message = "请选择前景图")
    private String image;
    @NotBlank(message = "请选择背景图")
    private String backImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }
}
