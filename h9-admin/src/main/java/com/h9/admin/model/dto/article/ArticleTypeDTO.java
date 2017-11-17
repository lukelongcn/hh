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

    @NotBlank(message = "分类名不能为空")
    @Length(max = 10,message = "分类名称过长")
    private String name;

    @ApiModelProperty(value = "排序",required = true)
    //@NotNull(message = "排序不能为空")
    @Min(value = 0,message = "排序号不能小于0")
    @Max(value = 127,message = "排序号不能大于127")
    private Integer sort ;
    
    @Min(value = 0,message = "请传入正确的状态")
    @Max(value = 1,message = "请传入正确的状态")
    @NotNull(message = "状态不能为空")
    private Integer enable;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort==null?1:sort;
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
