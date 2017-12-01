package com.h9.api.service;

import com.h9.api.model.vo.HomeVO;
import com.h9.api.model.vo.UpdateInfoVO;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.common.MailService;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/10/30.
 */
@Service
public class HomeService {

    @Resource
    private BannerRepository bannerRepository;
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ConfigService configService;
    @Resource
    private AnnouncementReposiroty announcementReposiroty;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private MailService mailService;

    @SuppressWarnings("Duplicates")
    public Result homeDate() {
        Map<String, List<HomeVO>> voMap = new HashMap<>();
        List<Banner> bannerList = bannerRepository.findActiviBanner(new Date(), 1);
        if (!CollectionUtils.isEmpty(bannerList)) {
            bannerList.stream().filter(b -> b.getLocation().equals(1)).forEach(banner -> {
                BannerType bannerType = banner.getBannerType();
                HomeVO convert = new HomeVO(banner);
                List<HomeVO> list = voMap.get(bannerType.getCode());

                if (list == null) {
                    List<HomeVO> tempList = new ArrayList<>();
                    tempList.add(convert);
                    voMap.put(bannerType.getCode(), tempList);
                } else {
                    if (bannerType.getCode().equals("ideaBanner")) {
                        if (list.size() >= 3) {
                            return;
                        }
                    }
                    list.add(convert);
                }
            });
        }

        Map<String, String> preLink = configService.getMapConfig("preLink");
        String articlelink = preLink.get("article");

        List<Article> articleList = articleRepository.findActiveArticle(new Date());

        if (!CollectionUtils.isEmpty(articleList)) {
            articleList.forEach(article -> {
                ArticleType articleType = article.getArticleType();

                String url = article.getUrl();
                //有外链接取外链接,没有拼上文章详情地址
                HomeVO convert = new HomeVO(article, StringUtils.isBlank(url) ? articlelink + article.getId() : url);

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


        List<Announcement> announcementList = announcementReposiroty.findActived(new Date());

        String announcemented = preLink.get("announcement");
        List<HomeVO> collect = announcementList.stream()
                .map(announcement -> {
                    String url = announcement.getUrl();
                    HomeVO convert = new HomeVO(announcement, StringUtils.isBlank(url) ? announcemented + announcement.getId() : url);
                    return convert;
                })
                .collect(Collectors.toList());
        //查询公告
        voMap.put("noticeArticle", collect);
        return Result.success(voMap);
    }

    @Resource
    VersionRepository versionRepository;

    public Result version(Integer version, Integer type) {

        //检查是否有新版本
        Version lastVersion = versionRepository.findLastVersion(type, version);
        if(lastVersion.getVersionNumber().equals(version)) return Result.success("已是最新版本");

        List<Version> forceUpdateVersion = versionRepository.findForceUpdateVersion(type, version);
        if (CollectionUtils.isEmpty(forceUpdateVersion)) {
            //当前版本与最新版本之间没有强制升级版,返回最新版
            return Result.success(new UpdateInfoVO(lastVersion));
        }else{
            //有强制升级版，返回最新版，将升级策略改成强升
            UpdateInfoVO updateInfoVO = new UpdateInfoVO(lastVersion);
            updateInfoVO.setUpdateType(3);
            return Result.success(updateInfoVO);
        }
    }
}
