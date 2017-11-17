package com.h9.api.model.vo;

import com.h9.common.db.entity.*;
import com.h9.common.utils.DateUtil;


/**
 * Created by itservice on 2017/10/30.
 */
public class HomeVO {

    private String imgUrl;
    private String link;
    private String title;
    private String content;
    private String code;
    private String createTime;
    private String type;
    private String userName;
    private String typeName;

    public HomeVO( Article article,String articlePreUrl) {
        ArticleType articleType = article.getArticleType();
        setImgUrl(article.getImgUrl());
        setContent("");
        setTitle(article.getTitle());
        setCode(articleType.getCode());
        String link = articlePreUrl + article.getId();
        setLink(link);
        setCreateTime(DateUtil.formatDate(article.getCreateTime(), DateUtil.FormatType.DAY));
        setType("article");
        setContent(article.getTitle());
        setUserName(article.getUserName());
        setTypeName(articleType.getName());
    }

    public HomeVO(Banner banner) {
        setImgUrl(banner.getIcon());
        setContent(banner.getContent());
        setTitle(banner.getTitle());
        setLink(banner.getUrl());
        BannerType bannerType = banner.getBannerType();
        setCode(bannerType.getCode());
        setCreateTime(DateUtil.formatDate(bannerType.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));
        setType("banner");
    }

    public HomeVO(Announcement announcement,String articlePreUrl) {
        setImgUrl(announcement.getImgUrl());
        setContent("");
        setTitle(announcement.getTitle());
        String link = articlePreUrl + announcement.getId();
        setLink(link);
        setCreateTime(DateUtil.formatDate(announcement.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));
        setType("article");
        setContent(announcement.getTitle());
        setTypeName("公告");
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
