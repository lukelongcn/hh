package com.h9.admin.model.dto.html;

import com.h9.admin.validation.Edit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * Created by Gonyb on 2017/11/9.
 */
@ApiModel("单网页管理")
public class HtmlContentDTO {
    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空",groups = Edit.class)
    private Long id;

    @ApiModelProperty(value = "名称",required = true)
    @NotBlank(message = "名称不能为空")
    private String name;
    @ApiModelProperty(value = "标示",required = true)
    @NotBlank(message = "标示不能为空")
    private String code;
    
    @ApiModelProperty(value = "标题",required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容",required = true)
    @NotBlank(message = "内容不能为空")
    private String content;
    
    @ApiModelProperty(value = "状态 1 启用 0 禁用 同一个code 只能有一个启用的",required = true)
    @NotNull(message = "状态不能为空")
    @Max(value = 1,message = "请填写正确的状态")
    @Min(value = 0,message = "请填写正确的状态")
    private Integer status = 1;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
