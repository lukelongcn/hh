package com.h9.api.model.vo;

import com.h9.common.db.entity.Article;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.User;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private Date startTime;//开始时间

    private Date endTime;//结束时间

    private Integer recommend = 1;//1 推荐 2 不推荐


    public ArticleVO(Article article) {
        BeanUtils.copyProperties(article,this);
    }

    public Long getId() {
        return id;
    }


    public ArticleType getArticleType() {
        return articleType;
    }


    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }


    public String getUrl() {
        return url;
    }

    public Date getStartTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse("2008-08-08 ");
    }


    public Date getEndTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.parse("2008-08-08");
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStartTime(Date startTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start= format.parse("2008-08-08");
            this.startTime = start;
        } catch (ParseException e) {
        }
    }

    public void setEndTime(Date endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date end= format.parse("2008-08-08");
            this.endTime = end;
        } catch (ParseException e) {
        }
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }
}
