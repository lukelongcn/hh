package com.h9.api.service;

import com.h9.api.model.vo.HomeVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Article;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.ArticleReposiroty;
import com.h9.common.db.repo.BannerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by itservice on 2017/10/30.
 */
@Service
public class HomeService {

    @Resource
    private BannerRepository bannerRepository;
    @Resource
    private ArticleReposiroty articleReposiroty;

    @SuppressWarnings("Duplicates")
    public Result homeDate() {
        Map<String, List<HomeVO>> voMap = new HashMap<>();
        List<Banner> bannerList = bannerRepository.findActiviBanner(new Date());
        if (!CollectionUtils.isEmpty(bannerList)) {
            bannerList.forEach(banner -> {
                BannerType bannerType = banner.getBannerType();
                HomeVO convert = HomeVO.convert(Banner.class, banner);
                List<HomeVO> list = voMap.get(bannerType.getCode());
                if (list == null) {
                    List<HomeVO> tempList = new ArrayList<>();
                    tempList.add(convert);
                    voMap.put(bannerType.getCode(), tempList);
                } else {
                    list.add(convert);
                }
            });
        }

        List<Article> articleList = articleReposiroty.findActiveAriticle(new Date());
        if (!CollectionUtils.isEmpty(articleList)) {
            articleList.forEach(article -> {
                ArticleType articleType = article.getArticleType();
                HomeVO convert = HomeVO.convert(Article.class, article);

                List<HomeVO> list = voMap.get(articleType.getCode());
                if (list == null) {
                    List<HomeVO> tempList = new ArrayList<>();
                    tempList.add(convert);
                    voMap.put(articleType.getCode(), tempList);
                } else {
                    list.add(convert);
                }
            });
        }

        return Result.success(voMap);
    }
}
