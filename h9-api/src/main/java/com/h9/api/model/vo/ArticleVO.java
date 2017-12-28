package com.h9.api.model.vo;

import com.h9.common.db.entity.config.Article;
import com.h9.common.db.entity.config.ArticleType;
import org.springframework.beans.BeanUtils;

/**
 * Created by 李圆
 * on 2017/11/11
 */
public class ArticleVO {
    private Long id;

    private ArticleType articleType;//标题类型

    private String title;//标题

    private String userName;//用户名

    private String content;//内容

    private String url;//跳转链接

    protected String createTime ;//创建时间

    private Integer recommend = 1;//1 推荐 2 不推荐

    private Integer enable;// 状态


    public ArticleVO(Article article) {
        BeanUtils.copyProperties(article,this);
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

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
