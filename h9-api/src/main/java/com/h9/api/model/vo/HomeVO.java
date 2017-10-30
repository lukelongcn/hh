package com.h9.api.model.vo;

import com.h9.common.db.entity.Article;
import com.h9.common.db.entity.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2017/10/30.
 */
public class HomeVO {

    private List<Banner> topBannerList = new ArrayList<Banner>();

    private List<Article> notice = new ArrayList<Article>();

    private List<Banner> navigationList = new ArrayList<Banner>();

    private List<Banner> ideaList = new ArrayList<Banner>();

    private List<Article> recommend = new ArrayList<Article>();

    private Map<String, String> popAd = new HashMap<>();

    public HomeVO( ) {
    }


    public List<Banner> getTopBannerList() {
        return topBannerList;
    }

    public void setTopBannerList(List<Banner> topBannerList) {
        this.topBannerList = topBannerList;
    }

    public List<Article> getNotice() {
        return notice;
    }

    public void setNotice(List<Article> notice) {
        this.notice = notice;
    }

    public List<Banner> getNavigationList() {
        return navigationList;
    }

    public void setNavigationList(List<Banner> navigationList) {
        this.navigationList = navigationList;
    }

    public List<Banner> getIdeaList() {
        return ideaList;
    }

    public void setIdeaList(List<Banner> ideaList) {
        this.ideaList = ideaList;
    }

    public List<Article> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Article> recommend) {
        this.recommend = recommend;
    }

    public Map<String, String> getPopAd() {
        return popAd;
    }

    public void setPopAd(Map<String, String> popAd) {
        this.popAd = popAd;
    }
}
