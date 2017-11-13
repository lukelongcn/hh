package com.h9.admin.model.dto.article;

import com.h9.admin.validation.Edit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *2017/11/4 17:24
 */
@ApiModel("文章类型")
public class ArticleTypeDTO {

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空",groups = Edit.class)
    private Long id;

    @NotBlank(message = "分类名不能为null")
    @Length(max = 10,message = "分类名称过长")
    private String name;
    
    private int sort = 1;
    
    @Min(value = 0,message = "请传入正确的enable")
    @Max(value = 1,message = "请传入正确的enable")
    @NotNull(message = "enable不能为空")
    private Integer enable;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
