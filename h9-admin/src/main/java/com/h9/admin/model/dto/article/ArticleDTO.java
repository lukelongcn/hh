package com.h9.admin.model.dto.article;

import com.h9.admin.validation.Edit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 2017/11/4 17:24
 */
@ApiModel("文章")
public class ArticleDTO {

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空",groups = Edit.class)
    private Long id;
    
    @ApiModelProperty(value = "类别id",required = true)
    @NotNull(message = "类别id不能为null")
    private Long articleTypeId;
    
    @ApiModelProperty(value = "标题",required = true)
    @NotBlank(message = "title不能为null")
    private String title;
    
    @ApiModelProperty(value = "内容",required = true)
    @NotBlank(message = "content不能为null")
    private String content;

    @ApiModelProperty(value = "作者名字",required = true)
    private String userName = "";
    
    @ApiModelProperty(value = "是否推荐到首页 1推荐 2不推荐",required = true)
    @NotNull(message = "recommend不能为null")
    @Max(value = 2,message = "请填写正确的recommend")
    @Min(value = 1,message = "请填写正确的recommend")
    private Integer recommend = 1;
    
    @ApiModelProperty(value = "外部链接")
    private String url;
    
    @ApiModelProperty(value = "1 启用 0禁用",required = true)
    @NotNull(message = "enable不能为null")
    @Max(value = 1,message = "请填写正确的enable")
    @Min(value = 0,message = "请填写正确的enable")
    private Integer enable;
    
    @ApiModelProperty(value = "排序 数字越大越靠前",required = true)
    private Integer sort = 1;
    
    @ApiModelProperty(value = "发布时间",required = true)
    @NotNull(message = "startTime不能为null")
    private Date startTime;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Long articleTypeId) {
        this.articleTypeId = articleTypeId;
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

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
