package com.h9.api.model.vo;

import com.h9.common.db.entity.Article;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import com.h9.common.utils.DateUtil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String typeName;


    @SuppressWarnings("Duplicates")
    public static HomeVO convert(Class clazz, Object obj, String articlePreUrl) {


        HomeVO vo = new HomeVO();
        if (clazz.getSimpleName().equalsIgnoreCase("Article")) {

            Article article = (Article) obj;
            ArticleType articleType = article.getArticleType();
            vo.setImgUrl(article.getImgUrl());
            vo.setContent("");
//            vo.setImgUrl(article);
            vo.setTitle(article.getTitle());
            vo.setCode(articleType.getCode());
            String link = articlePreUrl + article.getId();
            vo.setLink(link);
//            vo.setLink(article.getUrl());
            vo.setCreateTime(DateUtil.formatDate(article.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));
            vo.setType("article");
            vo.setContent(article.getTitle());
            vo.setTypeName(articleType.getName());
            return vo;
        }

        if (clazz.getSimpleName().equalsIgnoreCase("Banner")) {

            Banner banner = (Banner) obj;
            vo.setImgUrl(banner.getIcon());
            vo.setContent(banner.getContent());
            vo.setTitle(banner.getTitle());
            vo.setLink(banner.getUrl());
            BannerType bannerType = banner.getBannerType();
            vo.setCode(bannerType.getCode());
            vo.setCreateTime(DateUtil.formatDate(bannerType.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));
            vo.setType("banner");
            return vo;
        }

        return null;
    }


    @SuppressWarnings("Duplicates")
    public static HomeVO convert(Class clazz, Object obj) {

        HomeVO vo = new HomeVO();
        if (clazz.getSimpleName().equalsIgnoreCase("Article")) {

            Article article = (Article) obj;
            ArticleType articleType = article.getArticleType();
            vo.setImgUrl(article.getImgUrl());
            vo.setContent("");
//            vo.setImgUrl(article);
            vo.setTitle(article.getTitle());
            vo.setCode(articleType.getCode());
            //TODO
            vo.setLink(article.getUrl());
            vo.setCreateTime(DateUtil.formatDate(article.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));
            vo.setType("article");
            vo.setContent(article.getTitle());
            vo.setTypeName(articleType.getName());
            return vo;
        }

        if (clazz.getSimpleName().equalsIgnoreCase("Banner")) {

            Banner banner = (Banner) obj;
            vo.setImgUrl(banner.getIcon());
            vo.setContent(banner.getContent());
            vo.setTitle(banner.getTitle());
            vo.setLink(banner.getUrl());
            BannerType bannerType = banner.getBannerType();
            vo.setCode(bannerType.getCode());
            vo.setCreateTime(DateUtil.formatDate(bannerType.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));
            vo.setType("banner");
            return vo;
        }

        return null;
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
}
