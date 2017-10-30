package com.h9.api.service;

import com.h9.api.model.vo.HomeVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Article;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.ArticleReposiroty;
import com.h9.common.db.repo.BannerReposiroty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/10/30.
 */
@Service
public class HomeService {

    @Resource
    private BannerReposiroty bannerReposiroty;
    @Resource
    private ArticleReposiroty articleReposiroty;
    public Result homeDate() {
        HomeVO vo = new HomeVO();

        List<Banner> bannerLsit = bannerReposiroty.findActiviBanner(new Date());

        if (!CollectionUtils.isEmpty(bannerLsit)) {

            Map<String, List<Banner>> groupBannerMap = bannerLsit.stream()
                    .collect(Collectors.groupingBy(banner -> banner.getBannerType().getCode()));

            if (!CollectionUtils.isEmpty(groupBannerMap)) {

                List<Banner> navitionBannerList = groupBannerMap.get(BannerType.BannerTypeEnum.NAVITION_BANNER.getCode()+"");
                List<Banner> topBannerList = groupBannerMap.get(BannerType.BannerTypeEnum.TOP_BANNER.getCode()+"");
                List<Banner> ideaBannerList = groupBannerMap.get(BannerType.BannerTypeEnum.IDEAR_BANNER.getCode()+"");

                vo.setTopBannerList(topBannerList);
                vo.setNavigationList(navitionBannerList);
                vo.setIdeaList(ideaBannerList);

            }
        }

        List<Article> articleList = articleReposiroty.findActiveAriticle(new Date());
        if(!CollectionUtils.isEmpty(articleList)){

            Map<String, List<Article>> groupArticleMap = articleList.stream().collect(Collectors.groupingBy(article -> article.getArticleType().getCode()));

            if (!CollectionUtils.isEmpty(groupArticleMap)) {

                List<Article> noticeArticleList = groupArticleMap.get(ArticleType.ArticleTypeEnum.NOTICE.getCode()+"");
                List<Article> recommedList = groupArticleMap.get(ArticleType.ArticleTypeEnum.RECOMMEND.getCode()+"");
                vo.setRecommend(recommedList);
                vo.setNotice(noticeArticleList);

            }
        }
        return Result.success(vo);
    }
}
